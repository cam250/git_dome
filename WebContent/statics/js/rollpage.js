
//用来分页的,表示实现第一个from表单对象
function page_nav(frm,num){
	frm.currPageNo.value = num;
	frm.submit();  //将uerlist.jsp页面中第一个form表单给提交
}

function jump_to(frm,num){
    //alert(num);
	//验证用户的输入
	var regexp=/^[1-9]\d*$/;
	var totalPageCount = document.getElementById("totalPageCount").value;
	//alert(totalPageCount);
	if(!regexp.test(num)){
		alert("请输入大于0的正整数！");
		return false;
	}else if((num-totalPageCount) > 0){
		alert("请输入小于总页数的页码");
		return false;
	}else{
		page_nav(frm,num);
	}
}