<%--
- form.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="risk.form.label.reference" path="reference"/>
	<acme:input-moment code="risk.form.label.identificationDate" path="identificationDate"/>
	<acme:input-double code="risk.form.label.impact" path="impact"/>
	<acme:input-double code="risk.form.label.probability" path="probability"/>
	<acme:input-double code="risk.form.label.riskFactor" path="riskFactor" readonly="true"/>
	<acme:input-textbox code="risk.form.label.description" path="description"/>
	<acme:input-url code="risk.form.label.link" path="link"/>
</acme:form>

