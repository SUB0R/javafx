package application;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import application.Student;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Path;

public class MyController{
	Stage stage = new Stage();
	@FXML private Button btn_1;
	@FXML private TextField text_zgf;
	@FXML private TextField text_zdf;
	@FXML private TextField text_pjf;
	@FXML private TextField text_yx;
	@FXML private TextField text_yxb;
	@FXML private TextField text_lh;
	@FXML private TextField text_lhb;
	@FXML private TextField text_zd;
	@FXML private TextField text_zdb;
	@FXML private TextField text_jg;
	@FXML private TextField text_jgb;
	@FXML private TextField text_bjg;
	@FXML private TextField text_bjgb;
	@FXML private TextField file_name;
	@FXML private TextField sousuo;
	@FXML private TableView tableview;
	@FXML private TableColumn table_name;
	@FXML private TableColumn table_id;
	@FXML private TableColumn table_grade;
	
    ArrayList<String> word = new ArrayList<String> ();
	ArrayList<String> word1 = new ArrayList<String> ();
    List<Student> stu = new ArrayList<Student> ();
    @FXML public void sousuo() {
    	ArrayList<Integer> grade = new ArrayList<Integer> ();
    	ObservableList<Student> list = FXCollections.observableArrayList();
    	int count=0;
		for(int i=0;i<word.size();i++) {
			if(word1.get(i).contains(sousuo.getText())) {
				count++;
				String regex = "[\\p{Punct}]+";
				String words[] = word.get(i).split(regex);
            	int a = Integer.parseInt(words[2]);
            	
            	grade.add(a);
            	Student user = new Student();//����ֵ����
               	user.setName(words[1]);
               	user.setId(words[0]);
               	user.setGrade(a);
                        
               	table_name.setCellValueFactory(new PropertyValueFactory("name"));//ӳ��          
               	table_id.setCellValueFactory(new PropertyValueFactory("id"));         
               	table_grade.setCellValueFactory(new PropertyValueFactory("grade"));
               	list.add(user);
               	tableview.setItems(list);
			}
		}
		if(count!=0) {
            int max=0;
            int min=100;
            int sum=0 ,bjg=0 ,jg=0 ,zd=0 ,lh=0 ,yx=0 ;
            for(int i=0;i<count;i++) {
            	if(grade.get(i)<60) bjg++;
            	else if(grade.get(i)>=60 && grade.get(i)<70) jg++;
            	else if(grade.get(i)>=70 && grade.get(i)<80) zd++;
            	else if(grade.get(i)>=80 && grade.get(i)<90) lh++;
            	else yx++;
            	if(grade.get(i)<min) min=grade.get(i);
            	if(grade.get(i)>max) max=grade.get(i);
            	sum+=grade.get(i);
            }
            text_zgf.setText(String.valueOf(max));
            text_zdf.setText(String.valueOf(min));
            text_pjf.setText(String.valueOf(sum/count));
            text_yx.setText(String.valueOf(yx));
            text_lh.setText(String.valueOf(lh));
            text_zd.setText(String.valueOf(zd));
            text_jg.setText(String.valueOf(jg));
            text_bjg.setText(String.valueOf(bjg));
            text_yxb.setText(String.valueOf((double)(yx*100/count)));
            text_lhb.setText(String.valueOf((double)(lh*100/count)));
            text_zdb.setText(String.valueOf((double)(zd*100/count)));
            text_jgb.setText(String.valueOf((double)(jg*100/count)));
            text_bjgb.setText(String.valueOf((double)(bjg*100/count)));
        }else {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.titleProperty().set("����");
        	alert.headerTextProperty().set("û���ҵ��й�\" "+sousuo.getText()+" \"������");
        	alert.showAndWait();
        }
	}
    
    @FXML public void writetxtfile() {
    	if (word.size()!=0) {
	    	FileChooser fileChooser = new FileChooser();
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	        fileChooser.getExtensionFilters().add(extFilter);	        
	        File file = fileChooser.showSaveDialog(null);
	        if (file == null)
	            return;
	        if(file.exists()){//�ļ��Ѵ��ڣ���ɾ�������ļ�
	           file.delete();
	        }
	        String exportFilePath = file.getAbsolutePath();
	
	        try {
	            try (FileWriter writer = new FileWriter(exportFilePath);
	                 BufferedWriter out = new BufferedWriter(writer)
	            ) {
	            	for(int i=0;i<word1.size();i++) {
	            		if(word1.get(i).contains(sousuo.getText())) {
	            			String regex = "[\\p{Punct}]+";
	        				String words[] = word.get(i).split(regex);
	            			out.write(words[0]+","+words[1]+","+words[2]);
	            			out.newLine();
	            		}
	            	}
	            	out.flush(); 
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
        	alert.titleProperty().set("����");
        	alert.headerTextProperty().set("û��������Ҫ����");
        	alert.showAndWait();
    	}
    }
    	
	@FXML public void opentxtfile() {
		ObservableList<Student> list = FXCollections.observableArrayList();
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if(file==null) {
        	return;
        }
        empty();
        String exportFilePath = file.getAbsolutePath();
    	file_name.setText(exportFilePath);
        ArrayList<Integer> grade = new ArrayList<Integer> ();
        int count=0;
        int flag=0;
        try (FileReader reader = new FileReader(exportFilePath);
                BufferedReader br = new BufferedReader(reader) ;) {
               String line;
               while ((line = br.readLine()) != null) {
            	if(line.length()==0) continue;
        		String pattern = "(\\d){12},[\\u4e00-\\u9fa5]+,(\\d){1,3}";
        		Pattern r = Pattern.compile(pattern);
        		Matcher m = r.matcher(line);
        		
            	if(m.matches()==false ) {
            		Alert alert = new Alert(AlertType.ERROR);
                	alert.titleProperty().set("�ļ���ʽ����");
                	alert.headerTextProperty().set("��򿪷��ϸ�ʽҪ���txt�ļ�");
                	alert.showAndWait();
                	flag=1;
                	break;
            	}else {
            		word.add(line);
                	count++;
                	String regex = "[\\p{Punct}]+";
                	String words[] = line.split(regex);
                	int a = Integer.parseInt(words[2]);
                	
                	grade.add(a);
                	Student user = new Student();//����ֵ����
                   	user.setName(words[1]);
                   	user.setId(words[0]);
                   	user.setGrade(a);
                   	word1.add(words[0]+words[1]);
                    stu.add(user);
                   	list.add(user);
            	}

               }
           } catch (IOException e) {
               e.printStackTrace();
           }
        if(count!=0 && flag==0) {
            int max=0;
            int min=100;
            int sum=0 ,bjg=0 ,jg=0 ,zd=0 ,lh=0 ,yx=0 ;
            for(int i=0;i<count;i++) {
            	if(grade.get(i)<60) bjg++;
            	else if(grade.get(i)>=60 && grade.get(i)<70) jg++;
            	else if(grade.get(i)>=70 && grade.get(i)<80) zd++;
            	else if(grade.get(i)>=80 && grade.get(i)<90) lh++;
            	else yx++;
            	if(grade.get(i)<min) min=grade.get(i);
            	if(grade.get(i)>max) max=grade.get(i);
            	sum+=grade.get(i);
            }
            sousuo.setText(null);
            text_zgf.setText(String.valueOf(max));
            text_zdf.setText(String.valueOf(min));
            text_pjf.setText(String.valueOf(sum/count));
            text_yx.setText(String.valueOf(yx));
            text_lh.setText(String.valueOf(lh));
            text_zd.setText(String.valueOf(zd));
            text_jg.setText(String.valueOf(jg));
            text_bjg.setText(String.valueOf(bjg));
            text_yxb.setText(String.valueOf((double)(yx*100/count)));
            text_lhb.setText(String.valueOf((double)(lh*100/count)));
            text_zdb.setText(String.valueOf((double)(zd*100/count)));
            text_jgb.setText(String.valueOf((double)(jg*100/count)));
            text_bjgb.setText(String.valueOf((double)(bjg*100/count)));
        }
        if(flag==0) {
        	list.sorted();
           	table_name.setCellValueFactory(new PropertyValueFactory("name"));
           	table_id.setCellValueFactory(new PropertyValueFactory("id"));         
           	table_grade.setCellValueFactory(new PropertyValueFactory("grade"));
        	tableview.setItems(list);
        }
	}
	
	@FXML public void writebin() throws IOException {
		if (word.size()!=0) {
	    	FileChooser fileChooser = new FileChooser();
	        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SCORE files (*.score)", "*.score");
	        fileChooser.getExtensionFilters().add(extFilter);
	        File file = fileChooser.showSaveDialog(null);
	        if (file == null)
	            return;
	        if(file.exists()){//�ļ��Ѵ��ڣ���ɾ�������ļ�
	            file.delete();
	        }
	        String exportFilePath = file.getAbsolutePath();
	        
//	        ObservableList<Student> list = tableview.getItems();
	        try  {
	        	ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
                for (Student data : stu) {  
                	if(data.getId().contains(sousuo.getText())||data.getName().contains(sousuo.getText())) {
                		output.writeObject(data);  
                	}
                }
                System.out.println("����ɹ�");  
	        }catch (FileNotFoundException ex) {  
	        	System.out.println("��������ļ��ı��ɼ�������"); 
                ex.printStackTrace();  
	        }
	     
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
        	alert.titleProperty().set("����");
        	alert.headerTextProperty().set("û��������Ҫ����");
        	alert.showAndWait();
    	}
	}
	
	@FXML public void openbin() throws ClassNotFoundException {
		ObservableList<Student> list = FXCollections.observableArrayList();
		FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SCORE files (*.score)", "*.score");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(null);
        if(file==null) {
        	return;
        }
        empty();
        String exportFilePath = file.getAbsolutePath();
    	file_name.setText(exportFilePath);
        ArrayList<Integer> grade = new ArrayList<Integer> ();
        int count=0;
        int flag=0;
        File file2 = new File(exportFilePath);
        try (FileInputStream reader = new FileInputStream(file2);
        		ObjectInputStream br = new ObjectInputStream(reader) ;) {
               String line;
               while (true) {
            	Student stud = (Student)br.readObject();
            	line = stud.getId()+","+stud.getName()+","+stud.getGrade();
            	if(line.length()==0) break;
        		String pattern = "(\\d){12},[\\u4e00-\\u9fa5]+,(\\d){1,3}";
        		Pattern r = Pattern.compile(pattern);
        		Matcher m = r.matcher(line);
        		
            	if(m.matches()==false ) {
            		Alert alert = new Alert(AlertType.ERROR);
                	alert.titleProperty().set("�ļ���ʽ����");
                	alert.headerTextProperty().set("��򿪷��ϸ�ʽҪ���score�ļ�");
                	alert.showAndWait();
                	flag=1;
                	break;
            	}else {
            		word.add(line);
                	count++;
                	String regex = "[\\p{Punct}]+";
                	String words[] = line.split(regex);
                	int a = Integer.parseInt(words[2]);
                	
                	grade.add(a);
                	Student user = new Student();//����ֵ����
                   	user.setName(words[1]);
                   	user.setId(words[0]);
                   	user.setGrade(a);
                   	word1.add(words[0]+words[1]);
                    stu.add(user);
                   	list.add(user);
            	}

               }
           } catch (IOException e) {
               e.printStackTrace();
           }
        if(count!=0 && flag==0) {
            int max=0;
            int min=100;
            int sum=0 ,bjg=0 ,jg=0 ,zd=0 ,lh=0 ,yx=0 ;
            for(int i=0;i<count;i++) {
            	if(grade.get(i)<60) bjg++;
            	else if(grade.get(i)>=60 && grade.get(i)<70) jg++;
            	else if(grade.get(i)>=70 && grade.get(i)<80) zd++;
            	else if(grade.get(i)>=80 && grade.get(i)<90) lh++;
            	else yx++;
            	if(grade.get(i)<min) min=grade.get(i);
            	if(grade.get(i)>max) max=grade.get(i);
            	sum+=grade.get(i);
            }
            sousuo.setText(null);
            text_zgf.setText(String.valueOf(max));
            text_zdf.setText(String.valueOf(min));
            text_pjf.setText(String.valueOf(sum/count));
            text_yx.setText(String.valueOf(yx));
            text_lh.setText(String.valueOf(lh));
            text_zd.setText(String.valueOf(zd));
            text_jg.setText(String.valueOf(jg));
            text_bjg.setText(String.valueOf(bjg));
            text_yxb.setText(String.valueOf((double)(yx*100/count)));
            text_lhb.setText(String.valueOf((double)(lh*100/count)));
            text_zdb.setText(String.valueOf((double)(zd*100/count)));
            text_jgb.setText(String.valueOf((double)(jg*100/count)));
            text_bjgb.setText(String.valueOf((double)(bjg*100/count)));
        }
        if(flag==0) {
        	list.sorted();
           	table_name.setCellValueFactory(new PropertyValueFactory("name"));
           	table_id.setCellValueFactory(new PropertyValueFactory("id"));         
           	table_grade.setCellValueFactory(new PropertyValueFactory("grade"));
        	tableview.setItems(list);
        }
	}
	
	@FXML public void empty() {
		text_zgf.setText("");
		text_zdf.setText("");
		text_pjf.setText("");
		text_yx.setText("");
		text_yxb.setText("");
		text_lh.setText("");
		text_lhb.setText("");
		text_zd.setText("");
		text_zdb.setText("");
		text_jg.setText("");
		text_jgb.setText("");
		text_bjg.setText("");
		text_bjgb.setText("");
		sousuo.setText("");
		
		word.clear();
		word1.clear();
		stu.clear();
        tableview.setItems(null); //tableview���list
	}	

	@FXML public void zhuxing(){
		if(text_yx.getText().length()==0) {
			Alert alert = new Alert(AlertType.ERROR);
        	alert.titleProperty().set("����");
        	alert.headerTextProperty().set("����δ�����ļ�");
        	alert.showAndWait();
		}else {
			final String youxiu = "����";
		    final String lianghao = "����";
		    final String zhongdeng = "�е�";
		    final String jige = "����";
		    final String bujige = "������";
		    
		    stage.setTitle("ѧ���ɼ����η���ͼ");
	        final CategoryAxis xAxis = new CategoryAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        final BarChart<String,Number> bc = 
	            new BarChart<String,Number>(xAxis,yAxis);
	        bc.setTitle("ѧ���ɼ����η���ͼ");
	        xAxis.setLabel("�ɼ��ȼ�");       
	        yAxis.setLabel("����");
	        
	        
	        XYChart.Series series = new XYChart.Series();
	        series.setName("����");
	        series.getData().add(new XYChart.Data(youxiu, Integer.parseInt(text_yx.getText())));
	        
	        XYChart.Series series1 = new XYChart.Series();
	        series1.setName("����");
	        series1.getData().add(new XYChart.Data(lianghao, Integer.parseInt(text_lh.getText())));
	        
	        XYChart.Series series2 = new XYChart.Series();
	        series2.setName("�е�");
	        series2.getData().add(new XYChart.Data(zhongdeng, Integer.parseInt(text_zd.getText())));
	        
	        XYChart.Series series3 = new XYChart.Series();
	        series3.setName("����");
	        series3.getData().add(new XYChart.Data(jige, Integer.parseInt(text_jg.getText())));
	        
	        XYChart.Series series4 = new XYChart.Series();
	        series4.setName("������");
	        series4.getData().add(new XYChart.Data(bujige, Integer.parseInt(text_bjg.getText()) ));  
	        
	        Scene scene  = new Scene(bc,800,600);
	        bc.getData().addAll( series,series1,series2,series3,series4);
	        stage.setScene(scene);
	        stage.show();
		}
    }
	
	@FXML public void bingxing() {
		if(text_yx.getText().length()==0) {
			Alert alert = new Alert(AlertType.ERROR);
        	alert.titleProperty().set("����");
        	alert.headerTextProperty().set("����δ�����ļ�");
        	alert.showAndWait();
		}else {
			Scene scene = new Scene(new Group());
	        stage.setTitle("ѧ���ɼ����ͷ���ͼ");
	        stage.setWidth(500);
	        stage.setHeight(500);
	        ObservableList<PieChart.Data> pieChartData =
	                FXCollections.observableArrayList(
	                new PieChart.Data("����(90-100)", Integer.parseInt(text_yx.getText())),
	                new PieChart.Data("����(80-89)", Integer.parseInt(text_lh.getText())),
	                new PieChart.Data("�е�(70-79)", Integer.parseInt(text_zd.getText())),
	                new PieChart.Data("����(60-69)", Integer.parseInt(text_jg.getText())),
	                new PieChart.Data("������(0-59)",Integer.parseInt(text_bjg.getText()) ));
	        final PieChart chart = new PieChart(pieChartData);
	        chart.setTitle("ѧ���ɼ����ͷ���ͼ");

	        ((Group) scene.getRoot()).getChildren().add(chart);
	        stage.setScene(scene);
	        stage.show();
		} 
	}
}
