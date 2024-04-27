<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:hidden-data path="codeAuditId"/>
	<acme:input-textbox code="any.codeAudit.form.label.code" path="code" readonly="true"/>	
	<acme:input-moment code="any.codeAudit.form.label.execution" path="execution" readonly="true"/>
	<acme:input-select code="any.codeAudit.form.label.type" path="type" choices="${types}" readonly="true"/>
	
	<acme:input-textbox code="any.codeAudit.form.label.mark" path="mark" readonly="true"/>	
	<acme:input-textarea code="any.codeAudit.form.label.correctiveActions" path="correctiveActions"/>
	<acme:input-url code="any.codeAudit.form.label.link" path="link" readonly="true"/>
	<acme:input-select code="any.codeAudit.form.label.project" path="project" choices="${projects}" readonly="true" />
	<acme:button code="any.codeAudit.form.button.auditRecords" action="/any/audit-record/list?masterId=${id}"/>			
			
</acme:form>
