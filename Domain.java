package application;

import java.net.http.HttpHeaders;

import javafx.scene.control.Button;

public class Domain {
    private String ip;
    private String https;
    private String http;
    private HttpHeaders header;
    private boolean hashttp;
    private int status;
    
    
	public Domain(String ip, String https, String http, int status,HttpHeaders header) {
		super();
		this.ip = ip;
		this.https = https;
		this.http = http;
		this.hashttp=true;
		this.status = status;
		this.header = header;
		
	}
	//when domain only has https
	public Domain(String ip, String https, int status,HttpHeaders header) {
		this.ip = ip;
		this.https = https;
		this.hashttp=true;
		this.status = status;
		this.header = header;
	}
	//when domain only has http
	public Domain(int status,String ip, String http,HttpHeaders header) {
		this.ip = ip;
		this.http = http;
		this.hashttp=true;
		this.status = status;
		this.header = header;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHttps() {
		return https;
	}
	public void setHttps(String https) {
		this.https = https;
	}
	public String getHttp() {
		return http;
	}
	public void setHttp(String http) {
		this.http = http;
		this.hashttp = true;
	}
	public boolean isHashttp() {
		return hashttp;
	}
	public void setHashttp(boolean hashttp) {
		this.hashttp = hashttp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	public HttpHeaders getHeader() {
		return header;
	}
	public void setHeader(HttpHeaders header) {
		this.header = header;
	}
	@Override
	public String toString() {
		return "IP: " + ip + ", https: " + https + ", http: "+this.http+ ", status: " + status;
	}
    
    
}
