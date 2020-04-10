package com.wkit.lost.mybatis.core.snowflake.worker;

import com.wkit.lost.mybatis.utils.Ascii;
import lombok.extern.log4j.Log4j2;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

@Log4j2
public final class SequenceUtil {

    private SequenceUtil() {
    }

    public static long getMaxWorkerId(int workerBits) {
        return ~(-1L << workerBits);
    }

    public static long getMaxDataCenterId(int dataCenterBits) {
        return ~(-1L << dataCenterBits);
    }

    public static long getDefaultDataCenterId(int dataCenterBits) {
        long id = 0L;
        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(address);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                if (mac != null) {
                    id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                    id = id % (getMaxDataCenterId(dataCenterBits) + 1);
                }
            }
        } catch (Exception e) {
            log.warn("Failed to get the corresponding worker id according to MAC address: ", e);
        }
        return id;
    }

    public static long getDefaultWorkerId(int workerBits, int dataCenterBits) {
        StringBuilder builder = new StringBuilder();
        builder.append(getDefaultDataCenterId(dataCenterBits));
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (Ascii.isNullOrEmpty(name)) {
            builder.append(name.split("@")[0]);
        }
        return (builder.toString().hashCode() & 0xffff) % (getMaxWorkerId(workerBits) + 1);
    }
}
