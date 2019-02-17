package com.jeeplus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer extends Thread{
	private int port = 35581;
	private ServerSocket serverSocket;
	private ExecutorService executorService;
	private final int POOL_SIZE = 2;

	public MultiThreadServer() throws IOException {
		serverSocket = new ServerSocket(port);
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
		System.out.println("socket 服务已启动");
	}

	public void service() {
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				executorService.execute(new Handler(socket));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run () {
		while (!this.isInterrupted()) {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			this.service();
		}
	}

	public static void main(String[] args) throws IOException {
		new MultiThreadServer().service();
	}

}

class Handler implements Runnable {

	public static final String CHARCODE = "utf-8";

	private Socket socket;

	public Handler(Socket socket) {
		this.socket = socket;
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(socketOut, true);
	}

	private BufferedReader getReader(Socket socket) throws IOException {
		InputStream socketIn = socket.getInputStream();
		return new BufferedReader(new InputStreamReader(socketIn));
	}

	public void run() {
		BufferedReader br = null;
		PrintWriter out = null;
		try {
			br = getReader(socket);
			out = getWriter(socket);
			String msg = null;
			while ((msg = br.readLine()) != null) {
				System.out.println("接收：" + msg);
				out.println("接收完毕");
				out.flush();
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
			if (out != null) {
				out.close();
			}
		}
	}

}