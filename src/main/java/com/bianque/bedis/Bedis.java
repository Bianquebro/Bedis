package com.bianque.bedis;

import com.bianque.bedis.entities.SlotsResult;
import com.bianque.bedis.enums.CodisCommandEnum;
import com.bianque.bedis.utils.EnumUtil;
import redis.clients.jedis.*;
import redis.clients.jedis.exceptions.InvalidURIException;
import redis.clients.util.JedisURIHelper;
import redis.clients.util.Pool;
import redis.clients.util.SafeEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.net.URI;
import java.util.*;

public class Bedis extends Jedis {

    static{
        for(CodisCommandEnum codisCommand : CodisCommandEnum.values()){
            EnumUtil.addEnum(Protocol.Command.class, codisCommand.name());
        }
    }
    protected Pool<Bedis> dataSource = null;

    public void setBedisPool(BedisPool bedisPool) {
        this.dataSource = bedisPool;
    }

    public Bedis() {
    }

    public Bedis(String host) {
        URI uri = URI.create(host);
        if (uri.getScheme() == null || !uri.getScheme().equals("redis") && !uri.getScheme().equals("rediss")) {
            this.client = new BedisClient(host);
        } else {
            this.initializeClientFromURI(uri);
        }
    }

    public Bedis(String host, int port) {
        this.client = new BedisClient(host, port);
    }

    public Bedis(String host, int port, boolean ssl) {
        this.client = new BedisClient(host, port, ssl);
    }

    public Bedis(String host, int port, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this.client = new BedisClient(host, port, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public Bedis(String host, int port, int timeout) {
        this.client = new BedisClient(host, port);
        this.client.setConnectionTimeout(timeout);
        this.client.setSoTimeout(timeout);
    }

    public Bedis(String host, int port, int timeout, boolean ssl) {
        this.client = new BedisClient(host, port, ssl);
        this.client.setConnectionTimeout(timeout);
        this.client.setSoTimeout(timeout);
    }

    public Bedis(String host, int port, int timeout, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this.client = new BedisClient(host, port, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
        this.client.setConnectionTimeout(timeout);
        this.client.setSoTimeout(timeout);
    }

    public Bedis(String host, int port, int connectionTimeout, int soTimeout) {
        this.client = new BedisClient(host, port);
        this.client.setConnectionTimeout(connectionTimeout);
        this.client.setSoTimeout(soTimeout);
    }

    public Bedis(String host, int port, int connectionTimeout, int soTimeout, boolean ssl) {
        this.client = new BedisClient(host, port, ssl);
        this.client.setConnectionTimeout(connectionTimeout);
        this.client.setSoTimeout(soTimeout);
    }

    public Bedis(String host, int port, int connectionTimeout, int soTimeout, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this.client = new BedisClient(host, port, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
        this.client.setConnectionTimeout(connectionTimeout);
        this.client.setSoTimeout(soTimeout);
    }

    public Bedis(JedisShardInfo shardInfo) {
        this.client = new BedisClient(shardInfo.getHost(), shardInfo.getPort(), shardInfo.getSsl(), shardInfo.getSslSocketFactory(), shardInfo.getSslParameters(), shardInfo.getHostnameVerifier());
        this.client.setConnectionTimeout(shardInfo.getConnectionTimeout());
        this.client.setSoTimeout(shardInfo.getSoTimeout());
        this.client.setPassword(shardInfo.getPassword());
        this.client.setDb((long)shardInfo.getDb());
    }

    public Bedis(URI uri) {
        this.initializeClientFromURI(uri);
    }

    public Bedis(URI uri, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this.initializeClientFromURI(uri, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public Bedis(URI uri, int timeout) {
        this.initializeClientFromURI(uri);
        this.client.setConnectionTimeout(timeout);
        this.client.setSoTimeout(timeout);
    }

    public Bedis(URI uri, int timeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this.initializeClientFromURI(uri, sslSocketFactory, sslParameters, hostnameVerifier);
        this.client.setConnectionTimeout(timeout);
        this.client.setSoTimeout(timeout);
    }

    public Bedis(URI uri, int connectionTimeout, int soTimeout) {
        this.initializeClientFromURI(uri);
        this.client.setConnectionTimeout(connectionTimeout);
        this.client.setSoTimeout(soTimeout);
    }

    public Bedis(URI uri, int connectionTimeout, int soTimeout, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        this.initializeClientFromURI(uri, sslSocketFactory, sslParameters, hostnameVerifier);
        this.client.setConnectionTimeout(connectionTimeout);
        this.client.setSoTimeout(soTimeout);
    }

    private void initializeClientFromURI(URI uri) {
        if (!JedisURIHelper.isValid(uri)) {
            throw new InvalidURIException(String.format("Cannot open Redis connection due invalid URI. %s", uri.toString()));
        } else {
            this.client = new BedisClient(uri.getHost(), uri.getPort(), uri.getScheme().equals("rediss"));
            String password = JedisURIHelper.getPassword(uri);
            if (password != null) {
                this.client.auth(password);
                this.client.getStatusCodeReply();
            }

            int dbIndex = JedisURIHelper.getDBIndex(uri);
            if (dbIndex > 0) {
                this.client.select(dbIndex);
                this.client.getStatusCodeReply();
                this.client.setDb((long)dbIndex);
            }

        }
    }

    private void initializeClientFromURI(URI uri, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        if (!JedisURIHelper.isValid(uri)) {
            throw new InvalidURIException(String.format("Cannot open Redis connection due invalid URI. %s", uri.toString()));
        } else {
            this.client = new BedisClient(uri.getHost(), uri.getPort(), uri.getScheme().equals("rediss"), sslSocketFactory, sslParameters, hostnameVerifier);
            String password = JedisURIHelper.getPassword(uri);
            if (password != null) {
                this.client.auth(password);
                this.client.getStatusCodeReply();
            }

            int dbIndex = JedisURIHelper.getDBIndex(uri);
            if (dbIndex > 0) {
                this.client.select(dbIndex);
                this.client.getStatusCodeReply();
                this.client.setDb((long)dbIndex);
            }

        }
    }

    public ScanResult<String> slotsScan(int slotnum, String cursor) {
        return this.slotsScan(slotnum, cursor, new ScanParams());
    }

    public ScanResult<String> slotsScan(int slotnum, String cursor, ScanParams params) {
        this.checkIsInMultiOrPipeline();
        BedisClient client = (BedisClient)this.client;
        client.slotsScan(slotnum, Integer.valueOf(cursor), params);
        List<Object> result = client.getObjectMultiBulkReply();
        int newcursor = Integer.parseInt(new String((byte[])result.get(0)));
        List<String> results = new ArrayList();
        List<byte[]> rawResults = (List)result.get(1);
        Iterator iterator = rawResults.iterator();

        while(iterator.hasNext()) {
            byte[] bs = (byte[])iterator.next();
            results.add(SafeEncoder.encode(bs));
        }
        return new ScanResult(newcursor, results);
    }

    public List<SlotsResult> slotsDel(int... slots) {
        this.checkIsInMultiOrPipeline();
        BedisClient client = (BedisClient)this.client;
        client.slotsDel(slots);
        List<Object> response = client.getObjectMultiBulkReply();
        List<SlotsResult> result = new ArrayList<>();
        for(Object object : response){
            result.add(new SlotsResult(Integer.valueOf(((List)object).get(0).toString()), Integer.valueOf(((List)object).get(1).toString())));
        }
        return result;
    }

    public List<SlotsResult> slotsInfo(int start, int count) {
        this.checkIsInMultiOrPipeline();
        BedisClient client = (BedisClient)this.client;
        client.slotsInfo(start, count);
        List<Object> response = client.getObjectMultiBulkReply();
        List<SlotsResult> result = new ArrayList<>();
        for(Object object : response){
            result.add(new SlotsResult(Integer.valueOf(((List)object).get(0).toString()), Integer.valueOf(((List)object).get(1).toString())));
        }
        return result;
    }
}
