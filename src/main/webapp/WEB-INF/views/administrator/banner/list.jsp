<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.banner.list.label.displayStart" path="displayStart" width="20%"/>	
	<acme:list-column code="administrator.banner.list.label.displayEnd" path="displayEnd" width="20%" />	
	<acme:list-column code="administrator.banner.list.label.slogan" path="slogan" width="60%"/>
</acme:list>


<acme:button test="${showCreate}" code="administrator.banner.list.button.create" action="/administrator/banner/create?masterId=${masterId}"/>

