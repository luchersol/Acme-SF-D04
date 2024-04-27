<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="auditor.auditRecord.form.label.code" path="code"/>	
	<acme:input-moment code="auditor.auditRecord.form.label.startDate" path="startDate"/>
	<acme:input-moment code="auditor.auditRecord.form.label.endDate" path="endDate"/>
	<acme:input-select code="auditor.auditRecord.form.label.mark" path="mark" choices="${marks}" />
	
	<acme:input-url code="auditor.auditRecord.form.label.link" path="link"/>
	
	
	<jstl:choose>
		
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			
		
		<jstl:if test="${ draftMode == true}">
			<acme:submit code="auditor.auditRecord.form.button.update" action="/auditor/audit-record/update"/>
			<acme:submit code="auditor.auditRecord.form.button.delete" action="/auditor/audit-record/delete"/>
			<acme:submit code="auditor.auditRecord.form.button.publish" action="/auditor/audit-record/publish"/>		
			</jstl:if>
		
		</jstl:when>
	
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditRecord.form.button.create" action="/auditor/audit-record/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
	
</acme:form>
