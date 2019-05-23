<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!-- 引入Spring表单标签的标签库 -->
     <%@ taglib prefix="fm" uri="http://www.springframework.org/tags/form" %>
<%@include file="common/head.jsp"%>

<div class="right">
        <div class="location">
            <strong>你现在所在的位置是:</strong>
            <span>供应商管理页面 >> 供应商添加页面</span>
        </div>
        <div class="providerAdd">
           <fm:form method="post" modelAttribute="provider">
           		<fm:errors path="proCode"/><br/>
           		供应商编码:<fm:input path="proCode"/><br/>
           		<fm:errors path="proName"/><br/>
           		供应商名称:<fm:input path="proName"/><br/>
           		<fm:errors path="proContact"/><br/>
           		联系人:<fm:input path="proContact"/><br/>
           		<fm:errors path="proPhone"/><br/>
           		联系电话:<fm:input path="proPhone"/><br/>
           		联系地址:<fm:input path="proAddress"/><br/>
           		传真:<fm:input path="proFax"/><br/>
           		描述:<fm:input path="proDesc"/><br/>
           		<input type="submit" value="保存">
           </fm:form>
     </div>
</div>
</section>
<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/statics/js/provideradd.js"></script>
