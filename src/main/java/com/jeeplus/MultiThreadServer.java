package com.jeeplus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.jxc.entity.BalanceSale;
import com.jeeplus.modules.jxc.entity.BalanceSaleDetail;
import com.jeeplus.modules.jxc.service.BalanceSaleService;

public class MultiThreadServer extends Thread{
	private int port = 33581;
	private ServerSocket serverSocket;
	private ExecutorService executorService;
	private final int POOL_SIZE = 2;

	public MultiThreadServer() throws IOException {
		serverSocket = new ServerSocket(port);
		executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * POOL_SIZE);
		System.out.println("Balance Socket 服务正在启动...");
	}

	public void service() {
		System.out.println("Balance Socket 服务已启动");
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
				Thread.sleep(10000);
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
	
	public Log log = LogFactory.getLog(this.getClass());
	
	BalanceSaleService balanceSaleService = SpringContextHolder.getBean(BalanceSaleService.class);
	
	BalanceSale balanceSale = new BalanceSale();
	
	public static final String CHARCODE = "utf-8";
	
	private String regEx = "[\r\t\n]";

	private Socket socket;

	public Handler(Socket socket) {
		this.socket = socket;
	}

	private PrintWriter getWriter(Socket socket) throws IOException {
		OutputStream socketOut = socket.getOutputStream();
		return new PrintWriter(new OutputStreamWriter(socketOut, CHARCODE), true);
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
				log.debug("接收:" + msg);
				String replyMsg = this.dealMsg(msg, out);
				out.println(replyMsg);
				out.flush();
				log.debug("回复:" + replyMsg);
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
	
	public String dealMsg(String msg, PrintWriter out) {
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
					
					balanceSale.setBalanceNo(balanceNo);
					balanceSale.setSaleTime(saleDate);
					balanceSale.setSaleId(saleId);
					balanceSale.setStatus("0");
				}
			}
			// 电子秤请求PC数据
			else if ("UPL".equals(operCode)) {
				if ("TIM".equals(values[1])) {
					replyMsg.append("DWL\tTIM\r\n");
					replyMsg.append("TIM\t" + getTime() + "\r\n");
					replyMsg.append("END\tTIM\r\n");
					out.print(replyMsg.toString());
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
				balanceSale.setSaleTime(saleTime);
				balanceSale.setSaleNo(saleNo);
				balanceSale.setWholeNo(wholeNo);
				balanceSale.setCurrentNo(currentNo);
				balanceSale.setSellerCardNo(sellerCardNo);
				balanceSale.setSellerNo(sellerNo);
				balanceSale.setBuyerCardNo(buyerCardNo);
				balanceSale.setRealPay(realPay);
				balanceSale.setMoneyPay(moneyPay);
				balanceSale.setCardPay(cardPay);
				balanceSale.setIsRegularSale(isRegularSale);
				balanceSale.setPayType(payType);
			}
			// 销售记录详情 ORS
			else if ("RES".equals(operCode)) {
				BalanceSaleDetail balanceSaleDetail = new BalanceSaleDetail();
				balanceSaleDetail.setId("");
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
				balanceSaleDetail.setSaleNo(saleNo);
				balanceSaleDetail.setOrderNo(orderNo);
				balanceSaleDetail.setSalePrice(salePrice);
				balanceSaleDetail.setProductNo(productNo);
				balanceSaleDetail.setGroupNo(groupNo);
				balanceSaleDetail.setOfficeNo(officeNo);
				balanceSaleDetail.setCostPrice(costPrice);
				balanceSaleDetail.setAmount(amount);
				balanceSaleDetail.setUnit(unit);
				balanceSaleDetail.setIsReturn(isReturn);
				balanceSaleDetail.setTax(tax);
				balanceSaleDetail.setOriginPrice(orginPrice);
				balanceSaleDetail.setRealPrice(realPrice);
				balanceSaleDetail.setProductName(productName);
				balanceSaleDetail.setDelFlag(BalanceSaleDetail.DEL_FLAG_NORMAL);
				if (balanceSale.getBalanceSaleDetailList() == null) {
					balanceSale.setBalanceSaleDetailList(new ArrayList<BalanceSaleDetail>());
				}
				balanceSale.getBalanceSaleDetailList().add(balanceSaleDetail);
			}
			// 销售记录统计 ORE
			else if ("REE".equals(operCode)) {
				
			}
			// 请求、数据传输结束
			else if ("END".equals(operCode)) {
				if ("REP".equals(values[1])) {
					replyMsg.append("DWL\tREP\t" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + '\t' + values[3] + '\t' + values[4] + "\t" + "1\t" + "\r\n");
					out.print(replyMsg.toString());
					if (balanceSaleService.get(balanceSale) == null) {
						balanceSaleService.save(balanceSale);
					} else {
						log.debug("已上传" + balanceSale.toString());
					}
					balanceSale = new BalanceSale();
				}
			}
		}
		return replyMsg.toString();
	}
	
	public String getTime() {
		Date now = new Date();
		DateFormat format = new SimpleDateFormat("yy\tMM\tdd\thh\tmm\tss");
		String dateStr = format.format(now);
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