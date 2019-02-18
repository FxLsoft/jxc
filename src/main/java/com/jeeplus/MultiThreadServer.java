package com.jeeplus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import freemarker.template.SimpleDate;

public class MultiThreadServer extends Thread{
	private int port = 33581;
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
	
	private String regEx = "[\r\t\n]";

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
				out.println(this.dealMsg(msg));
				out.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("结束...");
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
	
	public String dealMsg(String msg) {
		String[] values = msg.split(regEx);
		StringBuffer replyMsg = new StringBuffer();
		if (values.length > 0) {
			String operCode = values[0];
			// 数据头
			if ("DWL".equals(operCode)) {
				// 销售记录
				if ("REP".equals(values[1])) {
					// 创建一条销售记录
					// 时间
					Date saleDate = this.dealTime(values[2]);
					// 称编号
					String balanceNo = values[3];
					// 唯一码
					String saleId = values[4];
				}
			}
			// 电子秤请求PC数据
			else if ("UPL".equals(operCode)) {
				if ("TIM".equals(values[1])) {
					replyMsg.append("DWL\tTIM\r\n");
					replyMsg.append("TIM\t" + getTime() + "\r\n");
					replyMsg.append("END\tTIM\r\n");
				}
			}
			// 销售记录统计 ORP
			else if ("REP".equals(operCode)) {
				// 销售编号
				String saleNo = values[1];
				// 销售时间
				Date saleTime = this.dealTime("20" + this.repairDate(values[2]) + this.repairDate(values[3]) + this.repairDate(values[4]) + this.repairDate(values[5]) + this.repairDate(values[6]) + this.repairDate(values[7]));
				// 全局累计记录
				String wholeNo = values[8];
				// 当前累计记录
				String currentNo = values[9];
				// 营业员编号
				String sellerNo = values[10];
				// 买方账号
				String buyerCardNo = values[11] + values[12];
				// 实收金额
				Double realPay = this.dealNumber(values[13]);
				// 收现
				Double moneyPay = this.dealNumber(values[14]);
				// 卡付
				Double cardPay = this.dealNumber(values[15]);
				// 是否异常销售
				String isRegularSale = values[16];
				// 卖方账号
				String sellerCardNo = values[17] + values[18];
				// 支付方式
				String payType = values[21];
			}
			// 销售记录详情 ORS
			else if ("RES".equals(operCode)) {
				// 销售编号
				String saleNo = values[1];
				// 序号
				String orderNo = values[2];
				// 销售价
				Double salePrice = this.dealNumber(values[3]);
				// 商品编号
				String productNo = values[4];
				// 组编号
				String groupNo = values[5];
				// 部门编号
				String officeNo = values[6];
				// 成本价
				Double costPrice = this.dealNumber(values[7]);
				// 重量
				Double amount = this.dealNumber(values[8]);
				// 单位
				String unit = values[9];
				// 是否退货
				String isReturn = values[10];
				// 税额
				Double tax = this.dealNumber(values[11]);
				// 原始单价
				Double orginPrice = this.dealNumber(values[12]);
				// 实际单价
				Double realPrice = this.dealNumber(values[13]);
				// 商品名称
				String productName = values[14];
			}
			// 销售记录统计 ORE
			else if ("REE".equals(operCode)) {
				
			}
			// 请求、数据传输结束
			else if ("END".equals(operCode)) {
				
			}
		}
		return replyMsg.toString();
	}
	
	public String getTime() {
		Date now = new Date();
		DateFormat format = new SimpleDateFormat("yy\tMM\tdd\thh\tmm\tss");
		String dateStr = format.format(now);
		System.out.println(dateStr);
		return dateStr;
	}
	
	public Date dealTime(String dateStr) {
		Date date = new Date();
		try {
			date = new SimpleDateFormat("yyyyMMddhhmmss").parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	public Double dealNumber(String str) {
		String[] values = str.split(",");
		return Double.parseDouble(values[0]) / Math.pow(10, Integer.parseInt(values[1]));
	}
	
	public String repairDate(String str) {
		if (str.length() > 1) {
			return str;
		} else {
			return "0" + str;
		}
	}
}