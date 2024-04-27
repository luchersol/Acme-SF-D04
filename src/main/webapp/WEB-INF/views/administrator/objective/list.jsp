<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.objective.list.label.title" path="title"/>
	<acme:list-column code="administrator.objective.list.label.description" path="description"/>
	<acme:list-column code="administrator.objective.list.label.instantiationMoment" path="instantiationMoment"/>
	<acme:list-column code="administrator.objective.list.label.priority" path="priority"/>
</acme:list>

<acme:button code="administrator.objective.list.button.create" action="/administrator/objective/create"/>



