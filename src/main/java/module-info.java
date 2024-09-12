open module com.osaebros {
    // Pi4J Modules
    requires com.pi4j;
    requires com.pi4j.library.pigpio;
    requires com.pi4j.library.linuxfs;
    requires com.pi4j.plugin.pigpio;
    requires com.pi4j.plugin.raspberrypi;
    requires com.pi4j.plugin.mock;
    requires com.pi4j.plugin.linuxfs;
    uses com.pi4j.extension.Extension;
    uses com.pi4j.provider.Provider;

    // for logging
    requires java.logging;

    //Server
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    // JavaFX
    requires javafx.base;
    requires javafx.controls;
    requires javafx.web;
    requires io.netty.all;
    requires io.netty.buffer;
    requires io.netty.handler;
    requires io.netty.transport;
    requires io.netty.codec.http;
    requires io.netty.codec.http2;
    requires io.netty.common;

    requires io.netty.codec;
//    requires io.netty.codec.dns;
//    requires io.netty.codec.haproxy;
    requires io.netty.resolver;

//    requires io.netty.codec.memcache;
//    requires io.netty.codec.mqtt;
//    requires io.netty.codec.redis;
//    requires io.netty.codec.smtp;
//    requires io.netty.codec.socks;
//    requires io.netty.codec.stomp;
//    requires io.netty.codec.xml;
//    requires io.netty.handler.proxy;

//    requires io.netty.resolver.dns;
//    requires io.netty.transport.unix.common;
//    requires io.netty.transport.rxtx;
//    requires io.netty.transport.sctp;
//    requires io.netty.transport.udt;

    // Module exports
}