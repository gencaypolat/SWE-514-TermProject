import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AsmToOutput {

	public static void main(String[] args) {
		
		ArrayList<String> assemblySource = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("/Users/sahinkapan/Desktop/AssembledProgram.txt")); //reading the AssembledProgram file
			String s;																								  //line by line																									
				
			while((s=br.readLine()) != null) {
				assemblySource.add(s);				//adding to assemblySource array list
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		String AC="";			//accumulator
		String B="";			
		String C="";			//registers						
		String D="";
		String ZF="0";			//zero flag which is initially 0
		String valueMemoryAddressOfB="";    //value that memory address B contains
		
		int a=1;			//when a is 0, zero flag becomes 1
				
		
		for(int i=0;i<assemblySource.size();i++) {
			
			String firstPart=assemblySource.get(i).substring(0, 2);		//taking first 2 digits of assembly source and attained to firstPart		
			String secondPart=assemblySource.get(i).substring(2, 6);	//taking the  rest of digits and attained to secondPart	
			
			
			//APPLYING INSTRUCTIONS BY CHECKING FIRST AND SECOND PARTSSS
			
			if(firstPart.equals("08")) {
				AC = secondPart;
			}else if(firstPart.equals("0D") && secondPart.equals("0003")) {
				C=AC;
			}else if(firstPart.equals("0D") && secondPart.equals("0002")) {
				B=AC;
				//System.out.println(B);
			}else if(firstPart.equals("0D") && secondPart.equals("0004")) {
				D=AC;
			}else if(firstPart.equals("71") && secondPart.equals("0003")) {
				int temp = Integer.parseInt(C.trim(), 16 );
				System.out.println((char)temp);
			}else if(firstPart.equals("71") && secondPart.equals("0002")) {
				int temp = Integer.parseInt(B.trim(), 16 );
				System.out.println((char)temp);
			}else if(firstPart.equals("71") && secondPart.equals("0004")) {
				int temp = Integer.parseInt(D.trim(), 16 );
				System.out.println((char)temp);
			}else if(firstPart.equals("09") && secondPart.equals("0003")) {
				AC=C;
			}else if(firstPart.equals("09") && secondPart.equals("0004")) {
				AC=D;
			}else if(firstPart.equals("09") && secondPart.equals("0002")) {
				AC=B;
			}else if(firstPart.equals("0E") && secondPart.equals("0002")) {
				valueMemoryAddressOfB=AC;
			}else if(firstPart.equals("19") && secondPart.equals("0003")) {
				int value = Integer.parseInt(C, 16);
				value++;
				C = Integer.toHexString(value);
			}else if(firstPart.equals("19") && secondPart.equals("0002")) {
				int value = Integer.parseInt(B, 16);
				value++;
				B = Integer.toHexString(value);
			}else if(firstPart.equals("19") && secondPart.equals("0004")) {
				int value = Integer.parseInt(D, 16);
				value++;
				D = Integer.toHexString(value);
			}else if(firstPart.equals("1D") && secondPart.equals("0003")) {
				int value = Integer.parseInt(C, 16);
				value--;
				C = Integer.toHexString(value);
			}else if(firstPart.equals("1D") && secondPart.equals("0002")) {
				int value = Integer.parseInt(B, 16);
				value--;
				B = Integer.toHexString(value);
			}else if(firstPart.equals("1D") && secondPart.equals("0004")) {
				int value2 = Integer.parseInt(D, 16);
				value2--;
				a=value2;
				D = Integer.toHexString(value2);
			}else if(a==0) {
				ZF="1";
			}else if(firstPart.equals("53") && ZF.equals("0")) {
				int toInt= Integer.parseInt(secondPart);
				toInt=toInt/2+2;
				i=i-toInt;
			}else if(ZF.equals("1")){
				continue;
			}else if(firstPart.equals("04")&& secondPart.equals("0000")) {
				break;
			}
			
			
		}

	}

}
