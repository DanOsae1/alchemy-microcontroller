package com.osaebros.server;

import com.osaebros.modules.ApplicationFXModuleController;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;
import java.util.logging.Logger;

public class AlchemyServer implements Runnable {
    private static final Logger log = Logger.getLogger(AlchemyServer.class.getName());
    static final boolean SSL = System.getProperty("ssl") != null;
    private Channel serverChannel;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    final ApplicationFXModuleController applicationFXModuleController;
    final Integer PORT;

    public AlchemyServer(Integer PORT, ApplicationFXModuleController controller) {
        this.PORT = PORT;
        this.applicationFXModuleController = controller;
    }

    @Override
    public void run() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new HttpObjectAggregator(65536));
                            ch.pipeline().addLast(new AlchemyClientHandler(applicationFXModuleController));

                        }
//                        @Override
//                        protected void initChannel(AbstractChannel abstractChannel) throws Exception {
//                            ChannelPipeline pipeline = abstractChannel.pipeline();
//                            pipeline.addLast(new ReadTimeoutHandler(15, TimeUnit.SECONDS));
//                            pipeline.addLast(new AlchemyClientHandler(applicationFXModuleController));
//                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(PORT).sync();
            serverChannel = f.channel();
            log.info("Server started on port " + PORT);

            // Wait until the server socket is closed.
            // This will block until the server is shut down.
            serverChannel.closeFuture().sync();

        } catch (InterruptedException e) {
            log.severe("An error occurred with the server %s".formatted(e.getMessage()));
        } finally {
            shutdownNetty();
        }
    }

    private void shutdownNetty() {
        log.info("Shutting down server...");
        if (serverChannel != null) {
            serverChannel.close();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
        log.info("Server shutdown complete.");
    }

    private SslContext handleSSL() throws CertificateException, SSLException {
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }
        return sslCtx;
    }
}
