package polyglot.ext.update.check;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.regex.*;

public class Check {

	private String oAPIPath = "D:\\oAPI_small.in";
	private String nAPIPath = "D:\\nAPI_small.in";
	private String rulePath = "D:\\rules_small.in";
	
	//the first three data collected by class RuleCheck
	private HashMap<String, HashMap<String, ArrayList<String>>>
		oldMethods;
	private HashMap<String, HashMap<String, ArrayList<String>>>
		newMethods;
	private HashMap<String, String> typeMappings;
	
	private HashMap<String, HashMap<String, ArrayList<String>>>
		methodsInOldAPI;
	private HashMap<String, HashMap<String, ArrayList<String>>>
		methodsInNewAPI;
	private HashMap<String, String>
		oldClassRelation = new HashMap<String, String>();
	private HashMap<String, String>
		newClassRelation = new HashMap<String, String>();
	
	@SuppressWarnings("unused")
	private void print(HashMap<String, HashMap<String, ArrayList<String>>> m) {
		System.out.println("methods are:");
		Iterator<Entry<String, HashMap<String, ArrayList<String>>>> 
			it = m.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HashMap<String, ArrayList<String>>>
				en = it.next();
			Iterator<Entry<String, ArrayList<String>>> itt 
				= en.getValue().entrySet().iterator();
			while (itt.hasNext()) {
				System.out.print(en.getKey() + " ");
				Entry<String, ArrayList<String>> enn = itt.next();
				System.out.print(enn.getKey()+ " ");
				for (String str : enn.getValue())
					System.out.print(str + " ");
				System.out.println();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private void printx(HashMap<String, String> m) {
		System.out.println("relations in API");
		Iterator<Entry<String, String>> 
			it = m.entrySet().iterator(); 
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			System.out.println(en.getKey() +
					" extends " + en.getValue());
		}
	}
	
	public Check(String oAPIPath, String nAPIPath, String rulePath) throws IOException {
		parserRules();
		methodsInOldAPI = 
			new HashMap<String, HashMap<String, ArrayList<String>>>();
		parserAPI(oAPIPath, oldClassRelation, methodsInOldAPI);
		methodsInNewAPI =
			new HashMap<String, HashMap<String, ArrayList<String>>>();
		parserAPI(nAPIPath, newClassRelation, methodsInNewAPI);
		//print(methodsInOldAPI);
		//print(methodsInNewAPI);
		typeMappingCheck();
		completenessCheck();
		methodsTypeCorrectCheck();
		//printx(oldClassRelation);
		//printx(newClassRelation);
	}
	
	public Check() throws IOException {
		parserRules();
		methodsInOldAPI = 
			new HashMap<String, HashMap<String, ArrayList<String>>>();
		parserAPI(oAPIPath, oldClassRelation, methodsInOldAPI);
		methodsInNewAPI =
			new HashMap<String, HashMap<String, ArrayList<String>>>();
		parserAPI(nAPIPath, newClassRelation, methodsInNewAPI);
//		print(methodsInOldAPI);
//		print(methodsInNewAPI);
		typeMappingCheck();
		completenessCheck();
		methodsTypeCorrectCheck();
	}
	
	public void parserRules() throws IOException {
		BufferedReader ruleReader = fileRead(rulePath);
		RuleCheck r = null;
		while (true) {
			String line = normal(ruleReader.readLine());
			if (line == null)
				break;
			if (line.trim().equals(""))
				continue;
			r = new RuleCheck(line);
		}
		oldMethods = r.getOldMethods();
		newMethods = r.getNewMethods();
		typeMappings = r.getTypeMapings();
	}
	
	public void parserAPI(String path, HashMap<String, String> cr,
			HashMap<String, HashMap<String, ArrayList<String>>> m) throws IOException {
		
		BufferedReader reader = fileRead(path);
		while (true) {
			String line = normal(reader.readLine());
			if (line == null)
				break;
			if (line.trim().equals(""))
				continue;
			String baseClass;
			String superClass;
			int x = line.indexOf("class");
			int y = line.indexOf("extends");
			int z = line.indexOf('{');
			if (x == -1 || z == -1)
				Error(line + "\\n" + "line above has a wrong format");
			if (y != -1) {
				baseClass = line.substring(x+5, y).trim();
				superClass = line.substring(y+7, z).trim();
				cr.put(baseClass, superClass);
			} else {
				baseClass = line.substring(x+5, z).trim();
			}
			String reg = "\\{[^\\{\\}]*\\}";
			Pattern pn = Pattern.compile(reg);
			Matcher mr = pn.matcher(line);
			if (!mr.find())
				continue;
			String s = mr.group();
			//maybe split(" *; *") is better
			String[] methCollections = s.substring(1, s.length()-1).split(";");
			for (int i = 0; i < methCollections.length; i++) {
				String str = methCollections[i];
				if (!str.trim().equals("")) {
					x = str.indexOf('(');
					y = str.indexOf(')');
					String current = str.substring(0, x).trim();
					ArrayList<String> paras = new ArrayList<String>();
					for (String para : str.substring(x+1, y).split(",")) {
						if (!para.trim().equals("")) {
							paras.add(para.trim());
						}
					}
					HashMap<String, ArrayList<String>> tmp
						= new HashMap<String, ArrayList<String>>();
					tmp.put(current, paras);
					if (!m.containsKey(baseClass)) {
						m.put(baseClass, tmp); 
					} else {
						m.get(baseClass).putAll(tmp);
					}
				}
			}
		}
	}
	
	//remove the { and }, and make each rule just in a line
	public String normal(String line) {
		return line;
	}
	
	public BufferedReader fileRead(String path) {
		BufferedReader oFin = null;
		try {
			oFin = new BufferedReader(
					new InputStreamReader(new FileInputStream(path)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return oFin;
	}
	
	public void Error(String errMsg) {
		System.err.println(errMsg);
		System.exit(-1);
	}
	
	//check if transformation preserve the class inheritance relationship
	public boolean typeMappingCheck() {
		Iterator<Entry<String, String>> it = oldClassRelation.entrySet().iterator(); 
		while (it.hasNext()) {
			Entry<String, String> en = it.next();
			String baseClass = typeMappings.get(en.getKey());
			String superClass = typeMappings.get(en.getValue());
			if (!newClassRelation.containsKey(baseClass))
				Error("do not preserve the class inheritance relation");
			if (!newClassRelation.get(baseClass).equals(superClass))
				Error(en.getKey() + " <: " + en.getValue() + " do not" +
						" imply that " + baseClass + " <: " + superClass);
		}
		return true;
	}
	
	//only N[oO] classes and classes in new API are valid
	public boolean methodsTypeCorrectCheck() {
		Iterator<Entry<String, HashMap<String, ArrayList<String>>>> 
			it = newMethods.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HashMap<String, ArrayList<String>>>
				en = it.next();
			String className = en.getKey();
			if (!className.startsWith("No") && 
					!className.startsWith("NO") &&
					!methodsInNewAPI.containsKey(className))
				Error("Class " + className + " is not in new API and is not a No class");
			HashMap<String, ArrayList<String>> m = en.getValue();
			Iterator<Entry<String, ArrayList<String>>> methIt
				= m.entrySet().iterator();
			while (methIt.hasNext()) {
				Entry<String, ArrayList<String>> methEN = methIt.next();
				String methName = methEN.getKey();
				if (!methodsInNewAPI.get(className).containsKey(methName))
					Error("Method " + methName + " is not in class " + className);
				else if (!listCom(methEN.getValue(), methodsInNewAPI.get(className).get(methName)))
					Error("Method " + methName + " is type incorrect");
			}
		}
		return true;
	}
	
	public boolean completenessCheck() {
		//Class and Method completeness
		Iterator<Entry<String, HashMap<String, ArrayList<String>>>>
			it = methodsInOldAPI.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HashMap<String, ArrayList<String>>>
				en = it.next();
			if (!typeMappings.containsKey(en.getKey()))
				Error("Class " + en.getKey() + " is not in old APIs");
			
			HashMap<String, ArrayList<String>> allMeths
				= en.getValue();
			HashMap<String, ArrayList<String>> methsInRules
				= oldMethods.get(en.getKey());
			
			Iterator<Entry<String, ArrayList<String>>> itt = allMeths.entrySet().iterator();
			while (itt.hasNext()) {
				Entry<String, ArrayList<String>> eenn = itt.next();
				if (!methsInRules.containsKey(eenn.getKey()))
						Error("Rules do not include method " + eenn.getKey());
				if (!listCom(eenn.getValue(), methsInRules.get(eenn.getKey())))
						Error("method parameters are incorrect of " + eenn.getKey());
			}
		}
		return true;
	}
	
	public boolean listCom(ArrayList<String> a1, ArrayList<String> a2) {
		if (a1.size() != a2.size())
			return false;
		for (int i = 0; i < a1.size(); i++) {
			if (!a1.get(i).equals(a2.get(i)))
				return false;
		}
		return true;
	}
}
