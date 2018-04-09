<%@ page import="model.Automobile" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>Total Price</title>
<LINK REL=STYLESHEET
      HREF="JSP-Styles.css"
      TYPE="text/css">
</head>

<body>
    <% Automobile auto = (Automobile) request.getSession().getAttribute("auto"); %>
    <h4>Here is what you selected:</h4>
    <table border="2" style="width:100%">
        <tr>
			<td><%= auto.getModel()%></td>
			<td>base price</td>
			<td><%=auto.getBasePrice()%></td>
		</tr>
        <% ArrayList<String> opSets = auto.getOptionsetsName();
            for(String opSet : opSets) {
                String selectedOption = request.getParameter(opSet);
                auto.setOptionChoice(opSet, selectedOption);
        %>

        <tr>
            <td><%= opSet %></td>
            <td><%= selectedOption %></td>
            <td><%= auto.getOptionChoicePrice(opSet) %></td>
        </tr>

        <%
            }
        %>
		<tr>
			<td><b>Total Cost</b></td>
			<td></td>
			<td><%=auto.getTotalPrice()%></td>
		</tr>
    </table>
</body>
</html>









