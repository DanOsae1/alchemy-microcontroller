package com.osaebros.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osaebros.modules.ApplicationFXModuleController;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.util.logging.Level;
import java.util.logging.Logger;

import static io.netty.util.CharsetUtil.UTF_8;

public class AlchemyClientHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private static final Logger log = Logger.getLogger(AlchemyClientHandler.class.getName());
    private final ObjectMapper objectMapper;
    private final ApplicationFXModuleController controller;

    public AlchemyClientHandler(ApplicationFXModuleController applicationFXModuleController) {
        this.controller = applicationFXModuleController;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        if (!request.decoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }

        if (request.method() != HttpMethod.POST) {
            sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }

        String contentType = request.headers().get(HttpHeaderNames.CONTENT_TYPE);
        if (contentType == null || !contentType.contains("application/json")) {
            sendError(ctx, HttpResponseStatus.UNSUPPORTED_MEDIA_TYPE);
            return;
        }

        String jsonBody = request.content().toString(UTF_8);
        try {
            log.info("Parsing request");
            AlchemyRequest alchemyRequest = objectMapper.readValue(jsonBody, AlchemyRequest.class);
            log.info("Request received %s".formatted(alchemyRequest));
            processRequest(ctx, alchemyRequest);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error processing request", e);
            sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void processRequest(ChannelHandlerContext ctx, AlchemyRequest request) {
        controller.dispense(request)
                .thenRun(() -> sendResponse(ctx, HttpResponseStatus.OK, "Request processed successfully"))
                .exceptionally(e -> {
                    log.log(Level.SEVERE, "Error in dispense operation", e);
                    sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
                    return null;
                });
    }

    private void sendResponse(ChannelHandlerContext ctx, HttpResponseStatus status, String message) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                status,
                Unpooled.copiedBuffer(message, UTF_8)
        );
        response.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8")
                .set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        sendResponse(ctx, status, "Error: " + status.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("New client connected: " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        log.info("Client disconnected: " + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.log(Level.SEVERE, "Exception caught", cause);
        ctx.close();
    }
}

