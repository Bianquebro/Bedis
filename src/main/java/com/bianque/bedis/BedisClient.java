package com.bianque.bedis;

import com.bianque.bedis.enums.CodisCommandEnum;
import redis.clients.jedis.Client;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.ScanParams;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocketFactory;
import java.util.ArrayList;
import java.util.List;

public class BedisClient extends Client {
    public BedisClient() {
    }

    public BedisClient(String host) {
        super(host);
    }

    public BedisClient(String host, int port) {
        super(host, port);
    }

    public BedisClient(String host, int port, boolean ssl) {
        super(host, port, ssl);
    }

    public BedisClient(String host, int port, boolean ssl, SSLSocketFactory sslSocketFactory, SSLParameters sslParameters, HostnameVerifier hostnameVerifier) {
        super(host, port, ssl, sslSocketFactory, sslParameters, hostnameVerifier);
    }

    public void slotsScan(int slotnum, int cursor, ScanParams params) {
        List<byte[]> args = new ArrayList();
        args.add(Protocol.toByteArray(slotnum));
        args.add(Protocol.toByteArray(cursor));
        args.addAll(params.getParams());
        this.sendCommand(Protocol.Command.valueOf(CodisCommandEnum.SLOTSSCAN.name()), (byte[][])args.toArray(new byte[args.size()][]));
    }
    public void slotsDel(int... slots) {
        List<byte[]> args = new ArrayList();
        for(int i=0,len=slots.length; i<len; i++){
            args.add(Protocol.toByteArray(slots[i]));
        }
        this.sendCommand(Protocol.Command.valueOf(CodisCommandEnum.SLOTSDEL.name()), (byte[][])args.toArray(new byte[args.size()][]));
    }

    public void slotsInfo(int start, int count) {
        List<byte[]> args = new ArrayList();
        args.add(Protocol.toByteArray(start));
        args.add(Protocol.toByteArray(count));
        this.sendCommand(Protocol.Command.valueOf(CodisCommandEnum.SLOTSINFO.name()), (byte[][])args.toArray(new byte[args.size()][]));
    }
}
