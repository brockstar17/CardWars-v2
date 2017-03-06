package com.github.brockstar17.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionUtils
{
	public static String getLocalIP() {

		try
		{
			InetAddress ipAddr = InetAddress.getLocalHost();
			return ipAddr.getHostAddress();
		} catch (UnknownHostException ex)
		{
			ex.printStackTrace();
		}

		return "No IP";
	}
}
