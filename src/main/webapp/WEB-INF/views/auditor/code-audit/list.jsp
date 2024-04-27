<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.codeAudit.list.label.code" path="code" width="20%"/>	
	<acme:input-textbox code="auditor.codeAudit.form.label.mark" path="mark" readonly="true"/>	
	<acme:list-column code="auditor.codeAudit.list.label.published" path="published" width="20%" />	
	<acme:list-column code="auditor.codeAudit.list.label.execution" path="execution" width="20%"/>
	<acme:list-column code="auditor.codeAudit.list.label.type" path="type" width="20%"/>
	<acme:list-column code="auditor.codeAudit.list.label.mark" path="mark" width="20%"/>
</acme:list>


<acme:button test="${showCreate}" code="auditor.code-audit.list.button.create" action="/auditor/code-audit/create?masterId=${masterId}"/>

