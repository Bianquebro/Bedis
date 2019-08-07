package com.bianque.bedis;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.JedisURIHelper;
import redis.clients.util.Pool;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.net.URI;

public class BedisPool extends Pool<Bedis> {

    public BedisPool() {
        this("localhost", 6379);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host) {
        this(poolConfig, host, 6379, 2000, null, 0, null);
    }

    public BedisPool(String host, int port) {
        this(new GenericObjectPoolConfig(), host, port, 2000, null, 0, null);
    }

    public BedisPool(String host) {
        URI uri = URI.create(host);
        if (JedisURIHelper.isValid(uri)) {
            String h = uri.getHost();
            int port = uri.getPort();
            String password = JedisURIHelper.getPassword(uri);
            int database = JedisURIHelper.getDBIndex(uri);
            boolean ssl = uri.getScheme().equals("rediss");
            this.internalPool = new GenericObjectPool(new BedisFactory(h, port, 2000, 2000, password, database, null, ssl, (SSLSocketFactory)null, (SSLParameters)null, (HostnameVerifier)null), new GenericObjectPoolConfig());
        } else {
            this.internalPool = new GenericObjectPool(new BedisFactory(host, 6379, 2000, 2000, null, 0, null, false, (SSLSocketFactory)null, (SSLParameters)null, (HostnameVerifier)null), new GenericObjectPoolConfig());
        }

    }

    public BedisPool(String host, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        URI uri = URI.create(host);
        if (JedisURIHelper.isValid(uri)) {
            String h = uri.getHost();
            int port = uri.getPort();
            String password = JedisURIHelper.getPassword(uri);
            int database = JedisURIHelper.getDBIndex(uri);
            boolean ssl = uri.getScheme().equals("rediss");
            this.internalPool = new GenericObjectPool(new BedisFactory(h, port, 2000, 2000, password, database, null, ssl, sslSocketFactory, sslParameters, hostnameVerifier), new GenericObjectPoolConfig());
        } else {
            this.internalPool = new GenericObjectPool(new BedisFactory(host, 6379, 2000, 2000, null, 0, null, false, (SSLSocketFactory)null, (SSLParameters)null, (HostnameVerifier)null), new GenericObjectPoolConfig());
        }

    }

    public BedisPool(URI uri) {
        this(new GenericObjectPoolConfig(), (URI)uri, 2000);
    }

    public BedisPool(URI uri, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(new GenericObjectPoolConfig(), uri, 2000, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public BedisPool(URI uri, int timeout) {
        this(new GenericObjectPoolConfig(), uri, timeout);
    }

    public BedisPool(URI uri, int timeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(new GenericObjectPoolConfig(), uri, timeout, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password) {
        this(poolConfig, host, port, timeout, password, 0, null);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, boolean ssl) {
        this(poolConfig, host, port, timeout, password, 0, null, ssl);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(poolConfig, host, port, timeout, password, 0, null, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port) {
        this(poolConfig, host, port, 2000, null, 0, null);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, boolean ssl) {
        this(poolConfig, host, port, 2000, null, 0, null, ssl);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(poolConfig, host, port, 2000, null, 0, null, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout) {
        this(poolConfig, host, port, timeout, null, 0, null);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, boolean ssl) {
        this(poolConfig, host, port, timeout, null, 0, null, ssl);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(poolConfig, host, port, timeout, null, 0, null, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database) {
        this(poolConfig, host, port, timeout, password, database, null);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, boolean ssl) {
        this(poolConfig, host, port, timeout, password, database, null, ssl);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(poolConfig, host, port, timeout, password, database, null, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, String clientName) {
        this(poolConfig, host, port, timeout, timeout, password, database, clientName, false, (SSLSocketFactory)null, (SSLParameters)null, (HostnameVerifier)null);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, String clientName, boolean ssl) {
        this(poolConfig, host, port, timeout, timeout, password, database, clientName, ssl, (SSLSocketFactory)null, (SSLParameters)null, (HostnameVerifier)null);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int timeout, String password, int database, String clientName, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(poolConfig, host, port, timeout, timeout, password, database, clientName, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, String host, int port, int connectionTimeout, int soTimeout, String password, int database, String clientName, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, new BedisFactory(host, port, connectionTimeout, soTimeout, password, database, clientName, ssl, sslSocketFactory, sslParameters, hostnameVerifier));
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, URI uri) {
        this(poolConfig, (URI)uri, 2000);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, URI uri, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(poolConfig, uri, 2000, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, URI uri, int timeout) {
        this(poolConfig, uri, timeout, timeout);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, URI uri, int timeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this(poolConfig, uri, timeout, timeout, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, URI uri, int connectionTimeout, int soTimeout) {
        super(poolConfig, new BedisFactory(uri, connectionTimeout, soTimeout, null, false, (SSLSocketFactory)null, (SSLParameters)null, (HostnameVerifier)null));
    }

    public BedisPool(GenericObjectPoolConfig poolConfig, URI uri, int connectionTimeout, int soTimeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(poolConfig, new BedisFactory(uri, connectionTimeout, soTimeout, null, uri.getScheme() != null && uri.getScheme().equals("rediss"), sslSocketFactory, sslParameters, hostnameVerifier));
    }

    public Bedis getResource() {
        Bedis bedis = super.getResource();
        bedis.setBedisPool(this);
        return bedis;
    }

    public void returnBrokenResource(Bedis resource) {
        if (resource != null) {
            this.returnBrokenResourceObject(resource);
        }

    }

    public void returnResource(Bedis resource) {
        if (resource != null) {
            try {
                resource.resetState();
                this.returnResourceObject(resource);
            } catch (Exception var3) {
                this.returnBrokenResource(resource);
                throw new JedisException("Resource is returned to the pool as broken", var3);
            }
        }

    }
}
