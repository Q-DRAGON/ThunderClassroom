package lzketh.model;

import lzketh.Utils;

import javax.print.DocFlavor;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;

public class Question extends BaseModel {
	public Integer id;
	public String title;
	public ArrayList<String> options;
	public String solution;

	public Question() {}

	public Question(String title, ArrayList<String> options, String solution) {
		this.title = title;
		this.options = options;
		this.solution = solution;
	}

	public byte[] getBytes() {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		StringBuilder sb = new StringBuilder();
		sb.append(id);
		sb.append("@@");
		sb.append(title);
		sb.append("@@");
		options.forEach(p -> {
			sb.append(p);
			sb.append("@@");
		});
		sb.delete(sb.length() - 2, sb.length());
		return Utils.getBytes(sb.toString());
	}

	public static void main(String[] args) {}
}
