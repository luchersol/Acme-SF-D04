<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:input-textarea code="administrator.objective.form.label.title" path="title"/>
	<acme:input-textarea code="administrator.objective.form.label.description" path="description"/>
	<acme:input-select code="administrator.objective.form.label.priority" path="priority" choices="${prioritys}"/>
	<acme:input-checkbox code="administrator.objective.form.label.status" path="status"/>
	<acme:input-moment code="administrator.objective.form.label.startDate" path="startDate"/>
	<acme:input-moment code="administrator.objective.form.label.endDate" path="endDate"/>
	<acme:input-url code="administrator.objective.form.label.link" path="link"/>
	<acme:input-checkbox code="administrator.objective.form.label.confirmation" path="confirmation"/>
		
	<acme:submit code="administrator.objective.form.button.post" action="/administrator/objective/post"/>
</acme:form>

