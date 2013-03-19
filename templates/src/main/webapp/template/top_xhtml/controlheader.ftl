<#--
	if the label position is top,
	then give the label it's own row in the table
-->
<tr>
    <td align="left" valign="top">
        <label <#if parameters.id??> for="${parameters.id?html}" </#if> class="label" >
            ${parameters.label?html}<#if parameters.required?default(false)><span class="required">*</span><#t/></#if>
        <#include "/${parameters.templateDir}/xhtml/tooltip.ftl" />
        </label>
    </td>
</tr>
<tr>
    <td align="left" />

