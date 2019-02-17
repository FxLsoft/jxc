package com.jeeplus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {

	public static final String CHARCODE = "utf-8";

	public static void main(String[] args) {

		Socket socket = null;
		int port = 35581;

		OutputStream socketOut = null;
		BufferedReader br = null;
		try {
			socket = new Socket("192.168.199.174", port);
			// 发送消息
			String msg = "send...";
			msg = msg + "\r\n";
			socketOut = socket.getOutputStream();
			socketOut.write(msg.getBytes());
			socketOut.flush();
			// 接收服务器的反馈
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String res = br.readLine();
			if (res != null) {
				System.out.println("c3:" + res);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (socketOut != null) {
				try {
					socketOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}