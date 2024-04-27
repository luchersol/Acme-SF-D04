<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:hidden-data path="codeAuditId"/>
	<acme:input-textbox code="auditor.codeAudit.form.label.code" path="code"/>	
	<acme:input-moment code="auditor.codeAudit.form.label.execution" path="execution"/>
	<acme:input-select code="auditor.codeAudit.form.label.type" path="type" choices="${types}"/>

	
	<jstl:if test="${_command == 'show'}">
		<acme:input-textbox code="auditor.codeAudit.form.label.mark" path="mark" readonly="true"/>	
	</jstl:if>	
	
	<jstl:if test="${_command == 'publish'}">
		<acme:input-textbox code="auditor.codeAudit.form.label.mark" path="mark" readonly="true"/>
	</jstl:if>	
	
	<acme:input-textarea code="auditor.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
	<acme:input-url code="auditor.codeAudit.form.label.link" path="link"/>
	<acme:input-select code="auditor.codeAudit.form.label.project" path="project" choices="${projects}" />

	
	<jstl:choose>
		
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			
		
		<jstl:if test="${ draftMode == true}">
			<acme:submit code="auditor.codeAudit.form.button.update" action="/auditor/code-audit/update"/>
			<acme:submit code="auditor.codeAudit.form.button.delete" action="/auditor/code-audit/delete"/>
		</jstl:if>
		

			<acme:button code="auditor.codeAudit.form.button.auditRecords" action="/auditor/audit-record/list?masterId=${id}"/>			
		
		
		<jstl:if test="${mark !=null && mark != 'F' && mark != 'F_MINUS' && draftMode == true}">
			<acme:submit code="auditor.codeAudit.form.button.publish" action="/auditor/code-audit/publish"/>
		</jstl:if>
		
		</jstl:when>
	
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.code-audit.form.button.create" action="/auditor/code-audit/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
		
</acme:form>
