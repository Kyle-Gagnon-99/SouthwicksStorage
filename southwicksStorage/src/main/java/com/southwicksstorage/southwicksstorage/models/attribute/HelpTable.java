/**
 * 
 */
package com.southwicksstorage.southwicksstorage.models.attribute;

import java.util.List;

/**
 * @author kyle
 *
 */
public class HelpTable {
	
	private List<HelpTableObject> tableList;
	
	public HelpTable(List<HelpTableObject> tableList) {
		this.tableList = tableList;
	}

	public List<HelpTableObject> getTableList() {
		return tableList;
	}

	public void setTableList(List<HelpTableObject> tableList) {
		this.tableList = tableList;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("	<div class=\"table-responsive\">" +
					  "		<table class=\"table table-bordered\">" +
					  "			<thead>" +
					  "				<tr>" +
					  " 				<th>Help Part</th>" +
					  " 				<th>Help Part Name</th>" +
					  " 				<th>Description</th>" +
					  "				</tr>" +
					  "			</thead>" +
					  "			<tbody>");
		for(int index = 0; index < tableList.size(); index++) {
			string.append(" 		<tr>" +
					  "					<td style=\"width:20%;\">" + tableList.get(index).getAnnotationNumber() + "</td>" +
					  "					<td style=\"width:25%;\">" + tableList.get(index).getAnnotationName() + "</td>" +
					  "					<td style=\"width:55%;\">" + tableList.get(index).getContent() + "</td>" +
					  "				</tr>");
		}
		string.append(" 		</tbody>" +
					  "		</table>" +
					  "	</div>");
		
		return string.toString();
	}

}
