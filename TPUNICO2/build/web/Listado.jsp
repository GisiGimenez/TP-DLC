<%@page contentType="text/html" pageEncoding="UTF-8" import="Logica.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>  
        <title>Lista de Documentos</title>
    </head>
    <body>
        <h1>Listado</h1>
        <%
        for(int i=0;i<ListaDocumentos.getLista().size();i++){
            String a=ListaDocumentos.getLista().get(i);
            out.write("<li>"+a+"</li>");
        }    
        %>
            
            
        
    </body>
</html>
