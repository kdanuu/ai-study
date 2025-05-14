/**
 * 질문 입력 폼 스크립트
 * 사용자가 입력한 질문을 처리하고 응답을 표시합니다.
 */

/**
 * getResponse - 기본 응답을 처리하는 함수
 * 사용자 질문을 서버로 전송하고 응답을 표시합니다.
 */
async function getResponse() {
    const question = document.getElementById('question').value;
    if (question.trim() === "") {
        alert("질문을 입력하세요");
        return;
    }

    const responseDiv = document.getElementById('response');
    responseDiv.innerText = '응답이 생성중입니다...';
    responseDiv.style.display = 'block';

    try {
        const response = await fetch(`/ask?message=${encodeURIComponent(question)}`);
        const result = await response.text();
        responseDiv.innerText = result;
    } catch (error) {
        console.error('Error:', error);
        responseDiv.innerText = '오류가 발생했습니다.';
    }
}

/**
 * getResponseOptions - 옵션을 적용한 응답을 처리하는 함수
 * 사용자 질문을 옵션과 함께 서버로 전송하고 응답을 표시합니다.
 */
async function getResponseOptions() {
    const question = document.getElementById('question').value;
    if (question.trim() === "") {
        alert("질문을 입력하세요");
        return;
    }

    const responseDiv = document.getElementById('response');
    responseDiv.innerText = '응답이 생성중입니다...';
    responseDiv.style.display = 'block';

    try {
        const response = await fetch(`/ask-ai?message=${encodeURIComponent(question)}`);
        const result = await response.text();
        responseDiv.innerText = result;
    } catch (error) {
        console.error('Error:', error);
        responseDiv.innerText = '오류가 발생했습니다.';
    }
}