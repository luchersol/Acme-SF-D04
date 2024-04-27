<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:input-moment code="administrator.objective.form.label.instantiationMoment" path="instantiationMoment"/>
	<acme:input-textarea code="administrator.objective.form.label.title" path="title"/>
	<acme:input-textarea code="administrator.objective.form.label.description" path="description"/>
	<acme:input-textarea code="administrator.objective.form.label.priority" path="priority"/>
	<acme:input-textarea code="administrator.objective.form.label.status" path="status"/>
	<acme:input-moment code="administrator.objective.form.label.startDate" path="startDate"/>
	<acme:input-moment code="administrator.objective.form.label.endDate" path="endDate"/>
	<acme:input-url code="administrator.objective.form.label.link" path="link"/>
	<jstl:choose>	 
		<jstl:when test="${_command == 'show'}">
			<acme:submit code="administrator.objective.form.button.update" action="/administrator/objective/update"/>
			<acme:submit code="administrator.objective.form.button.delete" action="/administrator/objective/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.objective.form.button.create" action="/administrator/objective/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>

