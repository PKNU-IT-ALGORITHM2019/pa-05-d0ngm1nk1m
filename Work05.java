package Algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Work05 {
    
    static Node_dict root = null;
    static int count; // 총단어수
    static String command; // 명령어
    static String word; // 찾을 단어
    
    public static Scanner keyboard;
    
    public static void main(String[] args) {
        
        read();
        
        while(true){
            
            System.out.print("$ ");
            keyboard = new Scanner(System.in);
            
            command = keyboard.next();
            if(command.equals("size")) {
                System.out.println(size(root));
            }
            else if(command.equals("find")) {
                word = keyboard.nextLine().trim();
                Node_dict result = find(root, word);
                if(result != null)
                    print(result);
            }
            else if(command.equals("add")) {
                add();
            }
            else if(command.equals("delete")) {
                word = keyboard.nextLine().trim();
                deleteKey(word);
            }
            else if(command.equals("deleteall")) {
                word = keyboard.nextLine().trim();
                deleteall(word);
            }
            else if(command.equals("exit"))
                break;
        }
    }
    
    public static void read() {
        
        try {
            Scanner    inFile = new Scanner(new File("shuffled_dict.txt"));
            
            while(inFile.hasNext()) { // detect End of File
                
                String buffer = inFile.nextLine();
                
                int len = buffer.length();
                int num1 = buffer.indexOf("(");
                int num2 = buffer.indexOf(")");
                
                if(len == num2 + 1) {
                    String voca = buffer.substring(0, num1 - 1);
                    String part = buffer.substring(num1);
                    root = insert(root, voca, part, null);
                }
                else {
                    String voca = buffer.substring(0, num1 - 1);
                    String part = buffer.substring(num1, num2 + 1);
                    String mean = buffer.substring(num2 + 2);
                    root = insert(root, voca, part, mean);
                }
            }
            
            inFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("No file");
            System.exit(9);
        }
    }
    
    public static int size(Node_dict root) {
        
        Node_dict p = root;
        
        if(p == null)
            return 0;
        else
            return (size(p.left) + 1 + size(p.right));
    }
    
    public static void add() {
        
        System.out.print("word: ");
        String buf1 = keyboard.next().trim();
        String buf2 = keyboard.nextLine().trim();
        System.out.print("class: ");
        buf2 = keyboard.nextLine().trim();
        if(buf2.equalsIgnoreCase(""))
            buf2 = "()";
        else
            buf2 = "(".concat(buf2).concat(".)");
        System.out.print("meaning: ");
        String buf3 = keyboard.nextLine().trim();
        
        root = insert(root, buf1, buf2, buf3);
        count++;
        
    }
    
    public static void deleteKey(String data)
    {
        if(command.equalsIgnoreCase("deleteall")) {
            root = deleteRec(root, data);
            return;
        }
        else if(find(root, data) != null) {
            root = deleteRec(root, data);
            System.out.println("Deleted successfully.");
            return;
        }
        
        System.out.println("Not found.");
    }
    
    public static Node_dict deleteRec(Node_dict root, String data)
    {
        if (root == null)
            return root;
        
        if (root.voca.compareToIgnoreCase(data) > 0)
            root.left = deleteRec(root.left, data);
        else if (root.voca.compareToIgnoreCase(data) < 0)
            root.right = deleteRec(root.right, data);
        else
        {
            if (root.left == null) {
                return root.right;
            }
            else if (root.right == null) {
                return root.left;
            }
            
            root.voca = minValue(root.right);
            
            root.right = deleteRec(root.right, root.voca);
        }
        
        return root;
    }
    
    public static void deleteall(String word2) {
        
        try {
            Scanner    inFile = new Scanner(new File(word2));
            
            int k = 0;
            
            while(inFile.hasNext()) { // detect End of File
                
                String buffer = inFile.nextLine();
                deleteKey(buffer);
                k++;
                
            }
            System.out.println(k + " words were deleted successfully.");
            inFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("No file");
            System.exit(9);
        }
        
    }
    
    public static String minValue(Node_dict root)
    {
        String minv = root.voca;
        while (root.left != null)
        {
            minv = root.left.voca;
            root = root.left;
        }
        return minv;
    }
    
    public static Node_dict find(Node_dict root, String word) {
        
        if(root == null || root.voca.equalsIgnoreCase(word))
            return root;
        if(root.voca.compareToIgnoreCase(word) >= 0)
            return find(root.left, word);
        
        return find(root.right, word);
    }
    
    public static Node_dict insert(Node_dict root, String data1, String data2, String data3) {
        
        Node_dict p = root;
        Node_dict newNode = new Node_dict(data1, data2, data3);
        
        if(p == null){
            return newNode;
        }
        
        if(p.voca.compareToIgnoreCase(newNode.voca) >= 0){
            p.left = insert(p.left, data1, data2, data3);
            return root;
        }
        else if(p.voca.compareToIgnoreCase(newNode.voca) < 0){
            p.right = insert(p.right, data1, data2, data3);
            return root;
        }
        
        return root;
        
    }
    
    public static void print(Node_dict result) {
        System.out.println("word: " + result.voca);
        System.out.println("class: " + result.part);
        if(result.mean == null)
            return;
        else
            System.out.println("meaning: " + result.mean);
    }
    
}
