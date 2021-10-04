package application;
	
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
 


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import javafx.scene.control.CheckBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.io.File;

import application.Domain;

public class Main extends Application {
    private List<String> 	arrayListDomain;
	private ObservableList<Domain> 	olDomain;
	private ListView<Domain> 		lvDomain;
	private Button start;
    private Button clear;
    private Button open;
    
	private HBox buttons;
	private VBox mainBox;
	private VBox menubars;
	private HBox undercontrol;
	private VBox statusBox;
	
	//menu
	private MenuBar menuBar;
    private Menu menuData ;
    private MenuItem menuItemOpen;
    private MenuItem menuItemSave;
    //sorting options
    private MenuBar menuBar2;
    private Menu menuData2 ;
    private MenuItem menuItemSortStatus;
    private MenuItem menuItemSortResponseLenght;
    private MenuItem menuItemSortVhost;
    //filter options
    private TextField txtStatus;
    private Label lblStatus;
    private CheckBox box403;
    private CheckBox box302;
    private CheckBox boxHttp;
    private CheckBox boxBypass;
    private HBox checkboxes;
    //saving options
    private Label lblSaving;
    private CheckBox boxSaveHttp;
    private CheckBox boxSaveIp;
    private CheckBox boxSaveStatus;
    private CheckBox boxSaveHeaders;
    private HBox checkboxesSaving;
    
    //adding stuff
    private TextField txtDoamin;
    private Button buttonDomain;
    private HBox DomainBox;
    //filter options variables
    private boolean bypass;
    private boolean http;
    private List<Integer> statusfilters;
    
    //saving options variables
    private boolean savehttp;
    private boolean saveIp;
    private boolean saveStatus;
    private boolean saveHeaders;
    //graphic display
    private TextArea headerdisplay;
	
    @Override
	public void start(Stage primaryStage) {
		try {
			//domain list 
			olDomain = FXCollections.observableArrayList();
			lvDomain = new ListView<Domain>();
			lvDomain.setItems(olDomain);
			lvDomain.setPrefWidth(450);
			
	
			//checkboxes saving
			this.lblSaving = new Label("   Saving Options");
			this.boxSaveHttp = new CheckBox(" http ");
			this.boxSaveIp = new CheckBox(" IP ");
			this.boxSaveStatus = new CheckBox(" Status ");
			this.boxSaveHeaders = new CheckBox(" Headers ");
			this.checkboxesSaving = new HBox(boxSaveHttp,boxSaveIp,boxSaveStatus,boxSaveHeaders);
			VBox.setMargin(checkboxesSaving, new Insets(10,10,10,10));
			//checkboxes filter
			this.box403 = new CheckBox(" 403 ");
			this.box302 = new CheckBox(" 302 ");
			this.boxHttp = new CheckBox(" http ");
			//CheckBox boxVhost = new CheckBox("Vhost");
			this.boxBypass = new CheckBox(" Bypass ");
			//checkboxes = new HBox(this.box302,this.box403,this.boxVhost,this.boxBypass);
	        HBox checkboxes = new HBox(box403,box302,boxBypass,boxHttp);
            VBox.setMargin(checkboxes, new Insets(10,10,10,10));
			
            //
            txtDoamin = new TextField();
            txtDoamin.setPromptText("eg www.example.com");
			buttonDomain = new Button("Add to list");
			this.DomainBox = new HBox(txtDoamin,buttonDomain);
            HBox.setMargin(this.DomainBox, new Insets(10,10,10,10));
            VBox.setMargin(this.DomainBox, new Insets(10,10,10,0));
            this.DomainBox.setSpacing(10);
	        //textfield you have to seperate by ,
            headerdisplay = new TextArea("");
            headerdisplay.setEditable(false);
			txtStatus = new TextField();
			txtStatus.setPromptText("eg 404,302,501");
			lblStatus = new Label("   Filter Status Codes");
			HBox h = new HBox(this.txtStatus,this.lblStatus);
			VBox.setMargin(h,new Insets(10,10,10,10) );
			statusBox = new VBox(h,checkboxes,lblSaving,checkboxesSaving,headerdisplay,this.DomainBox);
			HBox.setMargin(this.statusBox, new Insets(10,10,10,10));
			//Menus
			  MenuBar menuBar = new MenuBar();
		      Menu menuData = new Menu("File");
		      MenuItem menuItemDataOpen = new MenuItem("Open File ...");
		      MenuItem menuItemDataSave  = new MenuItem("Save To File");
		      menuData.getItems().add(menuItemDataOpen);
		      menuData.getItems().add(menuItemDataSave);
		      menuBar.getMenus().add(menuData);
		   //Menus for sorting
			  MenuBar menuBar2 = new MenuBar();
		      Menu menuData2 = new Menu("Sort");
		      MenuItem menuItemSortStatus = new MenuItem("Sort by Status ...");
		   //   MenuItem menuItemSortResponseLenght  = new MenuItem("Sort by Response Lenght ...");
		      MenuItem menuItemSortVhost = new MenuItem("Sort by Vhost ... ");
		      menuData2.getItems().add(menuItemSortStatus);
		     // menuData2.getItems().add(menuItemSortResponseLenght);
		      menuData2.getItems().add(menuItemSortVhost);
		      menuBar.getMenus().add(menuData2);
			this.menubars = new VBox(menuBar,menuBar2); 
		      
			
			//Buttons
			start = new Button("Start");
			clear = new Button("Clear");
			open = new Button("Browser");
			buttons = new HBox(this.start,this.clear,this.open);
		    
			 
		    this.buttonDomain.setOnAction(e -> {
		    	this.arrayListDomain.add(this.txtDoamin.getText());
		    	this.txtDoamin.setText("");
		    	for (String s :this.arrayListDomain) {
		    		System.out.println(s);
		    	}
		    });
		    this.statusfilters = new ArrayList<Integer>();
			clear.setOnAction(e-> {
				                   this.olDomain.clear();
				                   
			                       this.headerdisplay.clear();
			});
			start.setOnAction(e-> {
				try {
					this.startcheck();;
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
			});
			open.setOnAction(e ->{
				int selectedIdx =this.lvDomain.getSelectionModel().getSelectedIndex();
				if (selectedIdx > 0) {
					try {
						Desktop.getDesktop().browse(new URL(this.olDomain.get(selectedIdx).getHttps()).toURI());
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			menuItemDataSave.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				File selectedFile = fileChooser.showSaveDialog(primaryStage);
				this.save(selectedFile);
			});
			menuItemDataOpen.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				File selectedFile = fileChooser.showOpenDialog(primaryStage);	
				this.open(selectedFile);
			});
			menuItemSortStatus.setOnAction(e -> {
				this.sortbystatus();
			});
			menuItemSortVhost.setOnAction(e -> {
				this.sortbyVhost();;
			});
			
			//selection model
			lvDomain.getSelectionModel().selectedItemProperty().addListener(
					(ObservableValue<? extends Domain> ov,Domain old_val, Domain new_val) -> {
						if (new_val != null) {
							Map<String,List<String>> list =new_val.getHeader().map();
							this.headerdisplay.setText(this.beautifyHeaders(list));
						}
					}
					);

		    buttons.setSpacing(10);
			VBox.setMargin(buttons, new Insets(10,10,10,10));
			undercontrol = new HBox(this.lvDomain,this.statusBox);
			HBox.setMargin(undercontrol,new Insets(10, 10, 10,0));
			undercontrol.setPadding(new Insets(10,10,10,10));
			mainBox = new VBox(this.menubars,this.buttons,undercontrol);
			VBox.setMargin(this.mainBox, new Insets(10, 10, 10,0));
			//VBox.setMargin(personControls, new Insets(10, 10, 10, 10));
			Scene scene = new Scene(mainBox, 770, 500);
//			primaryStage.setFullScreen(true);
//			primaryStage.setFullScreenExitHint("");
			primaryStage.setTitle("Uptester");
			//primaryStage.getIcons().add(new Image("img/prof.png"));
		//	primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/img/prof.png")));
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	//sorting the data
	public void sortbyVhost() {
		this.olDomain.sort(new  IPcomparator());
		this.lvDomain.refresh();
	}
	public void sortbystatus() {
		this.olDomain.sort(new  StatusComparator());
		this.lvDomain.refresh();
	}
	
	
	public void open(File f) {

		BufferedReader in;
		try {
			List<String> list;
			try {
				list = Files.readAllLines(Paths.get(f.getAbsolutePath()), StandardCharsets.UTF_8);
				String a[] = list.toArray((new String[list.size()]));
				this.arrayListDomain = new ArrayList<String>();
				Collections.addAll(this.arrayListDomain, a);
				//System.out.println(this.arrayListDomain);
			} catch (IOException e) {
				e.printStackTrace();
			}
			 
			}
		
		finally {
			if (this.arrayListDomain.size()> 1){
				
			}
		
		}


		
	}
	public void save(File f) {
		//saving check for options
		this.savingcheck();
		FileWriter fw;
		PrintWriter out; // REMOVE and SEE
		try {
			// prepare file handling
			fw = new FileWriter(f, true);
			out = new PrintWriter(fw);

			for (Domain d : this.olDomain) {
				// parse to string
				boolean newline = true;
				String line = d.getHttps();
				out.print(line);   // "\n" added autmatically
				if(this.saveIp) {
					out.print(" "+d.getIp());
					newline = true;
				}
				if(this.saveStatus) { 
					out.println(" "+d.getStatus());
					newline = false;
				}
				if (newline) {
			        out.println("");
				}
				System.out.println("");
				if (this.savehttp) {
					out.println(d.getHttp());
				}
				System.out.println("");
				if (this.saveHeaders) {
					out.println(this.beautifyHeaders(d.getHeader().map()));
				}
				// write string
				// "\n" added autmatically
			}
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// out.close();
		}
		
	}
	private void savingcheck() {
		 if(this.boxSaveHttp.isSelected()) {
             this.savehttp = true;
	        }
	        if(this.boxSaveIp.isSelected()) {
	        	this.saveIp = true;
	        }
	        if(this.boxSaveStatus.isSelected()) {
	            this.saveStatus = true;
	}
	        if(this.boxSaveHeaders.isSelected()) {
	        	this.saveHeaders = true;
	        }
	}
	public void filtercheck() {
		this.statusfilters.clear();
		String[] sarry = this.txtStatus.getText().split(",");
	    this.statusfilters.add(404);
        this.bypass = false;
	    for (String a : sarry) {
        	try {
        	  Integer status = Integer.parseInt(a);
        	  this.statusfilters.add(status);
        	}
        	catch( java.lang.NumberFormatException e) {
        		System.out.println("The value in the filter ");
        	}
        if(this.box302.isSelected()) {
        	this.statusfilters.add(302);
        }
        if(this.box403.isSelected()) {
        	this.statusfilters.add(403);
        }
        if(this.boxBypass.isSelected()) {
            this.bypass = true;
        }
        if(this.boxHttp.isSelected()) {
           this.http = true;
        }
        
        }
		
	}
	public void startcheck() throws IOException, InterruptedException {
		//runs and checks all filters
		this.filtercheck();
		int t = 5;
		for (String f:this.arrayListDomain) {
			String name;
			boolean valid ;
			if(f.length()>3) {
				 name = f;
				 valid = true;
			}
			else {
				//this wont resolve 
				valid = false;
				name = "";
			}
			String url = "https://"+name;
			String url2 = "";
			System.out.println("https://"+name);
		
		try {
			var client = HttpClient.newHttpClient();
//			for (String f:this.arrayListDomain) {
//				String name;
//				boolean valid ;
//				if(f.length()>3) {
//					 name = f;
//					 valid = true;
//				}
//				else {
//					//this wont resolve 
//					valid = false;
//					name = "";
//				}
//				String url = "https://"+name;
//				String url2 = "";
//				System.out.println("https://"+name);
//				if (valid) {
				try {
					var request = HttpRequest.newBuilder(
						       URI.create(url)).version(Version.HTTP_1_1)
						   .header("accept", "*/*")
						   .timeout(Duration.ofSeconds(t))
						   .build();
                       // use the client to send the request
					try {
						var response = client.send(request,BodyHandlers.ofString());
						if(this.bypass) {
							if(response.statusCode() == 403) {
								System.out.println("Trying to bypass... " +url);
								var request2 = HttpRequest.newBuilder(
									       URI.create(url)).version(Version.HTTP_1_1)
									   .header("X-Forwarded-For", "127.0.0.1")
									   .timeout(Duration.ofSeconds(t))
									   .build();
						
								var response2 = client.send(request2,BodyHandlers.ofString());
								//not caring about user supplied filters bc theres something strange/suspicious going
								if(response2.statusCode() != 403) {
									InetAddress address = InetAddress.getByName(name); 
									//Map<String,​List<String>> h = response2.headers().map();
									Domain d = new Domain(this.beautifyIpAdress(address.toString()),url,url2,response2.statusCode(),response2.headers());
									this.olDomain.add(d);
									System.out.println("Bypass successfully "+ url +" Status:" +response2.statusCode());
				
								}
								else {
									System.out.println("Bypass failed... " +url);
								}
							}
						}
						if (this.http) {
							try {
								
								var request3 = HttpRequest.newBuilder(
									       URI.create("http://"+name)).version(Version.HTTP_1_1)
									   .header("accept", "*/*")
								   .timeout(Duration.ofSeconds(t))
									   .build();
						
								var response3 = client.send(request3,BodyHandlers.ofString());
								url2="http://"+name;

							}
							catch(java.net.ConnectException e1) {
								System.out.println("Address does not exist " +name);
								}
						}
							
						//needs more possibilitys and resolver 
						//System.out.println(response.statusCode() + ""+this.statusfilters.contains(response.statusCode()));
						if (!this.statusfilters.contains(response.statusCode())) {
							InetAddress address = InetAddress.getByName(name); 
							Domain d = new Domain(this.beautifyIpAdress(address.toString()),url,url2,response.statusCode(),response.headers());
							this.olDomain.add(d);
							
						}
					}
					catch (java.net.ConnectException e1) {
						System.out.println("Address does not exist" +name);
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			
		
		catch (Exception e){
			//todo throw exception(done)
			System.out.println(e.getMessage());
		}
	}
}

	
	//santiezes the ip address
	public String beautifyIpAdress(String address) {
		return address.substring(address.indexOf("/")+1);
	}
	public String beautifyHeaders(Map<String,List<String>> map) {
		String s = "";
		 for (Map.Entry<String,List<String>> entry : map.entrySet()) {
	            s = s  + entry.getKey() +" : " + entry.getValue().toString()+"\n ";
	    }
		return s;
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
