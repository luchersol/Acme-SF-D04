<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.codeAudit.list.label.code" path="code" width="20%"/>	
	<acme:list-column code="auditor.codeAudit.list.label.draftMode" path="draftMode" width="20%" />	
	<acme:list-column code="auditor.codeAudit.list.label.execution" path="execution" width="20%"/>
	<acme:list-column code="auditor.codeAudit.list.label.type" path="type" width="20%"/>
	<acme:list-column code="auditor.codeAudit.list.label.mark" path="mark" width="20%"/>
</acme:list>


<acme:button test="${showCreate}" code="auditor.code-audit.list.button.create" action="/auditor/code-audit/create?masterId=${masterId}"/>

