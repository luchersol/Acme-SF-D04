<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.risk.list.label.reference" path="reference"/>
	<acme:list-column code="administrator.risk.list.label.identificationDate" path="identificationDate"/>
	<acme:list-column code="administrator.risk.list.label.impact" path="impact"/>
	<acme:list-column code="administrator.risk.list.label.probability" path="probability"/>
</acme:list>

<acme:button code="administrator.risk.list.button.create" action="/administrator/risk/create"/>



