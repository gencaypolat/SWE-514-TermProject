/**
* 
* Assembly source to assembled program code
*
* @author Sahin Kapan-Student ID: 2021719075,Gencay Polat-Student ID: 2021719129
* @since Date: 11.01.2022
*/
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Swe514Asm {
	
	public static void main(String[] args) {
		
		ArrayList<String> assemblySource = new ArrayList<String>();   	//creation of assemblySource arraylist
		
		try {
																//Reading the AsmToHex file line by line 
			
			BufferedReader br = new BufferedReader(new FileReader("/Users/sahinkapan/Desktop/AsmToHex.txt"));
			String s;									 
			while((s=br.readLine()) != null) {
				assemblySource.add(s);							//Adding the lines to assemblySource arraylist
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> assembledProgram = new ArrayList<String>();		//creation of assembledProgram arraylist 
		
		int a=0;
		int b;
		
		
		for(int i=0;i<assemblySource.size();i++) {
			
			String[] splited = assemblySource.get(i).split("\\s+");				//splitting the elements of assemmblySource regarding to spaces		
			
			if(splited.length == 1 && splited[0].contentEquals("LOOP1:") ) {		
				a=i;
				continue;
			}
			if(splited.length == 2 && splited[1].contentEquals("LOOP1")) {
				b=i;
				int c=(b-a-2)*2;															//to calculate the distance between the "JNZ LOOP1" and "LOOP1:"
				int firstDigit = Integer.parseInt(Integer.toString(c).substring(0, 1));		
				int secondDigit = Integer.parseInt(Integer.toString(c).substring(1, 2));
				String bin =Integer.toBinaryString(0x10 | firstDigit).substring(1);			//copnverting to HEX
				String bin2 =Integer.toBinaryString(0x10 | secondDigit).substring(1);
				bin=bin+bin2;
				bin=new StringBuilder(bin).insert(0, "1100000000").toString(); 				//inserting addressing mode and the rest of zeros.
				splited[1]=bin;
				
			}else if(splited.length == 2 && splited[1].chars().allMatch( Character::isDigit )) {		//controlling whether the second part is number or not
				splited[1]=Integer.toBinaryString(0x10000 | Integer.parseInt(splited[1])).substring(1); //if it is, then convert to binary  															
				splited[1] = new StringBuilder(splited[1]).insert(0, "00").toString();					//inserting addressing mode to second part
			}else if(splited.length == 2 && splited[1].contains("'")) {									//controlling whether second part is char or not
				splited[1] = splited[1].replace("\'", "");
				int value = splited[1].charAt(0);												//if it is, then take the ASCII number and return the binary version
				splited[1]=Integer.toBinaryString(0x10000 | value).substring(1);				
				splited[1] = new StringBuilder(splited[1]).insert(0, "00").toString();			//inserting addressing mode to second part
			}
			
			
			for(int j=0;j<splited.length;j++) {
				
				//WRITING OF THE BINARY VERSION OF GIVEN HEX NUMBERS IN DOCUMENT
				
				if(splited[j].contentEquals("LOAD")) {										
					splited[j]="000010";
				}else if(splited[j].contentEquals("STORE")) {
					splited[j]="000011";
				}else if(splited[j].contentEquals("C")) {
					splited[j]="0000000000000011";
					splited[j] = new StringBuilder(splited[j]).insert(0, "01").toString();
				}else if(splited[j].contentEquals("B")) {
					splited[j]="0000000000000010";
					splited[j] = new StringBuilder(splited[j]).insert(0, "01").toString();
				}else if(splited[j].contentEquals("D")) {
					splited[j]="0000000000000100";
					splited[j] = new StringBuilder(splited[j]).insert(0, "01").toString();
				}else if(splited[j].contentEquals("MYDATA")) {
					splited[j]="0000000000101101";
					splited[j] = new StringBuilder(splited[j]).insert(0, "00").toString();
				}else if(splited[j].contentEquals("PRINT")) {
					splited[j]="011100";
			    }else if(splited[j].contentEquals("INC")) {
					splited[j]="000110";
			    }else if(splited[j].contentEquals("DEC")) {
					splited[j]="000111";
			    }else if(splited[j].contentEquals("JNZ")) {
					splited[j]="010100";
			    }else if(splited[j].contentEquals("HALT")) {
					splited[j]="000001";
					splited[j] = new StringBuilder(splited[j]).insert(splited[j].length(), "00").toString();
					splited[j] = new StringBuilder(splited[j]).insert(splited[j].length(), "0000000000000000").toString();
			    }else if(splited[j].contentEquals("[B]")) {
					splited[j]="0000000000000010";
					splited[j] = new StringBuilder(splited[j]).insert(0, "10").toString();
					
				}}
			String joined2 = String.join("", splited);
			assembledProgram.add(joined2);}
		
		ArrayList<String> assembledProgram2 = new ArrayList<String>();
		
		//CONVEERTING 24 BINARY NUMBER TO 6 HEX NUMBER
		
		for(int j=0;j<assembledProgram.size();j++) {
			
			String [] hexNumbers = new String[6];
			
			int digitNumber = 1;
		    int sum = 0;
		    String binary = assembledProgram.get(j);
		    for(int i = 0; i < binary.length(); i++){
		        if(digitNumber == 1)
		            sum+=Integer.parseInt(binary.charAt(i) + "")*8;
		        else if(digitNumber == 2)
		            sum+=Integer.parseInt(binary.charAt(i) + "")*4;
		        else if(digitNumber == 3)
		            sum+=Integer.parseInt(binary.charAt(i) + "")*2;
		        else if(digitNumber == 4 || i < binary.length()+1){
		            sum+=Integer.parseInt(binary.charAt(i) + "")*1;
		            digitNumber = 0;
		            if(sum < 10) {
		            	hexNumbers[(i-3)/4]=String.valueOf(sum);
		            }
		            else if(sum == 10) {
		            	hexNumbers[(i-3)/4]="A";
		            }
		            else if(sum == 11) {
		            	hexNumbers[(i-3)/4]="B";
		            }
		            else if(sum == 12) {
		            	hexNumbers[(i-3)/4]="C";
		            }
		            else if(sum == 13) {
		            	hexNumbers[(i-3)/4]="D";
		            }else if(sum == 14) {
		            	hexNumbers[(i-3)/4]="E";
		            }
		            else if(sum == 15) {
		            	hexNumbers[(i-3)/4]="F";
		            }
		            sum=0;
		        }
		        digitNumber++;  
		    }
		    String str = String.join("", hexNumbers);
		    assembledProgram2.add(str);									//added to assembledProgram2 arraylist
		}
		
		System.out.println(assembledProgram2);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/sahinkapan/Desktop/AssembledProgram.txt")); //writing into AssembledProgram.txt file
			
			for(int i=0;i<assembledProgram2.size();i++) {
				bw.write(String.valueOf(assembledProgram2.get(i))+"\n");	
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
