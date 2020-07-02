function validate() {
    var pwd1 = document.getElementById("rpassword").value;
    var pwd2 = document.getElementById("rpassword2").value;
    <!-- 对比两次输入的密码 -->
    if(pwd1 == pwd2) {
        document.getElementById("tishi").innerHTML="<font color='green'>两次密码相同</font>";
        document.getElementById("submit").disabled = false;
    }
    else {
        document.getElementById("tishi").innerHTML="<font color='red'>两次密码不相同</font>";
        document.getElementById("submit").disabled = true;
    }
}
document.querySelector('.img__btn').addEventListener('click', function() {
    document.querySelector('.content').classList.toggle('s--signup')
})


