console.log('memberRegister.js Join Success');

const emailRegExp = new RegExp("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$");
// 이메일 정규표현식 : 간단한 이메일 정규표현식
const pwdRegExp = new RegExp("^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,20}$");
// 비밀번호 정규표현식 : 최소 하나의 영문자와 숫자의 조합으로 6~20글자의 문자열
const nickNameRegExp = new RegExp("^[A-Za-z0-9]{4,10}$");
// 닉네임 정규표현식 : 특수문자를 제외한 영문자와 숫자로 구성된 4~10글자의 문자열

// 타당성 검사 결과 값
let emailIsOK = -1;
let pwdIsOK = -1;
let nickNameIsOK = -1;

document.addEventListener('input', (e)=>{

    // 이메일 타당성 검사
    if(e.target.id == 'email'){
        let emailVal = document.getElementById('email').value;
        // 클라이언트가 입력한 이메일

        emailIsOK = Validation(emailRegExp, emailVal);
        console.log('Validation % e:'+emailIsOK+' & p:'+pwdIsOK+' & n:'+nickNameIsOK);

        
        if(emailIsOK == -1){
            let emailWarningDiv = document.getElementById('emailWarningDiv');
            emailWarningDiv.innerText = '옳바른 이메일 구조가 아닙니다. 다시 입력해 주세요.';
            emailWarningDiv.hidden = false;
        }else if(emailIsOK == 1){
            document.getElementById('emailWarningDiv').hidden = true;
        };

        regBtn();
    };

    // 비밀번호 타당성 검사
    if(e.target.id == 'pwd'){
        let pwdVal = document.getElementById('pwd').value;

        pwdIsOK = Validation(pwdRegExp, pwdVal);
        console.log('Validation % e:'+emailIsOK+' & p:'+pwdIsOK+' & n:'+nickNameIsOK);

        if(pwdIsOK == -1){
            document.getElementById('pwdWarningDiv').hidden = false;
        }else if(pwdIsOK == 1){
            document.getElementById('pwdWarningDiv').hidden = true;
        };

        regBtn();
    };

    // 닉네임 타당성 검사
    if(e.target.id == 'nickName'){
        let nickVal = document.getElementById('nickName').value;
        
        nickNameIsOK = Validation(nickNameRegExp, nickVal);
        console.log('Validation % e:'+emailIsOK+' & p:'+pwdIsOK+' & n:'+nickNameIsOK);

        if(nickNameIsOK == -1){
            document.getElementById('nickNameWarningDiv').hidden = false;
        }else if(nickNameIsOK == 1){
            document.getElementById('nickNameWarningDiv').hidden = true;
        };

        regBtn();
    };
});

// 타당성 검사 함수
function Validation(regexp, value){
    if(regexp.test(value)){
        return 1; // 통과
    }else{
        return -1; // 탈락
    };
};

// regBtn On&Off 함수
function regBtn(){
    const regBtn = document.getElementById('regBtn');
    if(emailIsOK == 1 && pwdIsOK == 1 && nickNameIsOK == 1){
        regBtn.disabled = false;
    }else{
        regBtn.disabled = true;
    }
}

document.getElementById('email').addEventListener('blur', ()=>{
    // 이메일 중복 검사

    let emailVal = document.getElementById('email').value;

    if(emailVal.trim() === ''){
        // 이메일 값이 비어있다면
        emailIsOK = -1;
        let emailWarningDiv = document.getElementById('emailWarningDiv');
        emailWarningDiv.innerText = '옳바른 이메일 구조가 아닙니다. 다시 입력해 주세요.';
        emailWarningDiv.hidden = false;
        return;
    }
        
    emailSelectToServer(emailVal).then(result =>{
        if(result > 0){
            let emailWarningDiv = document.getElementById('emailWarningDiv');
            emailWarningDiv.innerText = '중복된 이메일 입니다. 다시 작성해 주세요.';
            emailWarningDiv.hidden = false;
            emailIsOK = -1;
            document.getElementById('email').focus();
            console.log('Validation % e:'+emailIsOK+' & p:'+pwdIsOK+' & n:'+nickNameIsOK);
        }else{
            emailIsOK = 1;
            console.log('Validation % e:'+emailIsOK+' & p:'+pwdIsOK+' & n:'+nickNameIsOK);
        }

        regBtn();
    });
});

// 이메일 중복 검사 비동기 통신
async function emailSelectToServer(email){
    try {
        const url = '/member/getEmail';
        const config = {
            method:'post',
            body:email
        };
        const resp = await fetch(url, config);
        const result = await resp.text();
        console.log('emailSelectToServer Result : ', result);
        return result;
    } catch (error) {
        console.log(error);
    };
};