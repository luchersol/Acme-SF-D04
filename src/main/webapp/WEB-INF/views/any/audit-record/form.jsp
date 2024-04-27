<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="any.auditRecord.form.label.code" path="code" readonly="true"/>	
	<acme:input-moment code="any.auditRecord.form.label.startDate" path="startDate" readonly="true"/>
	<acme:input-moment code="any.auditRecord.form.label.endDate" path="endDate" readonly="true"/>
	<acme:input-select code="any.auditRecord.form.label.mark" path="mark" choices="${marks}" readonly="true"/>
	<acme:input-url code="any.auditRecord.form.label.link" path="link" readonly="true"/>
	
</acme:form>
