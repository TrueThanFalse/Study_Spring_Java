console.log("boardRegister Join Success");

// 트리거 버튼 클릭하면 file upload 버튼 클릭
document.getElementById('trigger').addEventListener('click', ()=>{
    document.getElementById('files').click();
});

// 정규표현식을 활용한 업로드 금지 파일 설정
const regExp = new RegExp("\.(exe|sh|bat|dll|jar|msi)$"); // 업로드 금지 확장자 정규표현식 설정 완료
const maxFileSize = 1024*1024*20 // 20MB, 파일 최대 크기(WebConfig 설정과 동일하게)

function fileValidation(fileName, fileSize){
    if(regExp.test(fileName)){
        return 0;
    }else if(fileSize > maxFileSize){
        return 0;
    }else{
        return 1;
    }
}