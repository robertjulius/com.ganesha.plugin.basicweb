<#assign hasFieldErrors = parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]??/>
<div <#rt/><#if parameters.id??>id="wwgrp_${parameters.id}"<#rt/></#if> class="wwgrp">
	
<#if hasFieldErrors>
<div <#rt/><#if parameters.id??>id="wwerr_${parameters.id}"<#rt/></#if> class="wwerr">
<#list fieldErrors[parameters.name] as error>
    <div<#rt/>
    <#if parameters.id??>
     errorFor="${parameters.id}"<#rt/>
    </#if>
    class="errorMessage">
             ${error?html}
    </div><#t/>
</#list>
</div><#t/>
</#if>

<#if parameters.label??>
<#if parameters.labelposition?default("top") == 'top'>
<div <#rt/>
<#else>
<span <#rt/>
</#if>
<#if parameters.id??>id="wwlbl_${parameters.id}"<#rt/></#if> class="wwlbl">
    <label <#t/>
<#if parameters.id??>
        for="${parameters.id?html}" <#t/>
</#if>
<#if hasFieldErrors>
        class="errorLabel"<#t/>
<#else>
        class="label"<#t/>
</#if>
    ><#t/>
<#if parameters.required?default(false)>
        <span class="required">*</span><#t/>
</#if>
        ${parameters.label?html}
<#include "/${parameters.templateDir}/xhtml/tooltip.ftl" />
	</label><#t/>
<#if parameters.labelposition?default("top") == 'top'>
</div> <br /><#rt/>
<#else>
</span> <#rt/>
</#if>
</#if>