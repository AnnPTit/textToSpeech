let vocabList = [];
let currentIndex = 0;
let correct = 0;
let lang = true;
let b64AudioFile;


function showWord() {
    if (currentIndex >= vocabList.length) {
        let point = (correct / vocabList.length) * 10; // Tính điểm chính xác
        document.getElementById("card").innerHTML = `<h2>Hoàn thành! Điểm của bạn là: ${point.toFixed(2)}</h2>`;
        return;
    }

    const wordData = vocabList[currentIndex];
    document.getElementById("word").textContent = lang ? wordData.word : wordData.meaning;
    document.getElementById("wordType").textContent = "(" + wordData.type + ")";
    document.getElementById("user-input").value = "";
    document.getElementById("result").textContent = "";
}

function checkAnswer() {
    const wordData = vocabList[currentIndex];
    const correctAnswer = lang ? wordData.meaning : wordData.word; // Nghĩa tiếng Anh
    const userAnswer = document.getElementById("user-input").value.trim();

    console.log(correctAnswer);
    if (userAnswer.toLowerCase() === correctAnswer.toLowerCase()) {
        document.getElementById("result").textContent = "✅ Chính xác!";
        setTimeout(() => {
            currentIndex++;
            correct++;
            showWord();
        }, 1000);
    } else {
        document.getElementById("result").textContent = "❌ Sai rồi! Hãy thử lại.";
        document.getElementById("answer").textContent = "Đáp án : " + correctAnswer;
        setTimeout(() => {
            document.getElementById("answer").textContent = "";
            currentIndex++;
            showWord();
        }, 1000);
    }
}

function handleKeyPress(event) {
    if (event.key === "Enter") {
        checkAnswer();
    }
}

async function makeSound() {
    const wordData = vocabList[currentIndex];
    const word = lang ? wordData.word : wordData.meaning;
    let b64 = await getSound(word);
    b64AudioFile = "data:audio/mpeg;base64," + b64.data;
    let audioPlayer = document.getElementById("audio-player")
    audioPlayer.src = b64AudioFile;
    audioPlayer.play().catch(error => console.error("Error playing audio:", error));
}

async function getSound(text) {
    try {
        const response = await fetch('https://api.englishtest.vn/api/chats/tts', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Accept-Language': 'vi-VN,vi;q=0.9,fr-FR;q=0.8,fr;q=0.7,en-US;q=0.6,en;q=0.5',
                'Content-Type': 'application/json',
                'Authorization': `Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiMmI4MmMwMGM0ZjI1ZjRjYzhmOTNlMzE3ODBkZjEwNTk1NmRjMDFlMmU0MjU1YTUzOGI1MjhjOGRmYTcwNzEzNGE0MGY5MWIxY2ZmY2ZjNmIiLCJpYXQiOjE3NDAwNjE5NDUuMDYwNzAzLCJuYmYiOjE3NDAwNjE5NDUuMDYwNzA4LCJleHAiOjE3NzE1OTc5NDUuMDU3NDYsInN1YiI6IjI2NjYiLCJzY29wZXMiOltdfQ.k1iuljqqOhPXYRAMTwJ8aK4M-T5YOlinDcAuZRWLxzErKU57L0PZrKyFnI_UtM-EeB5jokkLLrvAmXl-x_ePmOKMIEZDOmXTS2McOkj-Wr4v3L0CcD7ammy_xev0OXNG6h37G-92EyMI6m5kYt8jTvIZsyg_Ch6GQc-of3PHAmaCKhfVZWSvUj8y4sJLmX5XGOiv-L9661jRYUUGmL7eO8DNwy38OUNPXChprAz4a6FcfPhS5Rl_kI1CDxND6tps7SPCTZtYPjl6bPD6aobU_wzP6EEdkOjgSSmHxnS3zAj6gT5JB9MsWOy0ee7YFh3oUtDoe4cyRJAC7IlfBO-9jNLNKh4JXIMo1dsM_4dDaR6eR5gaQmUcxxj-v_3fU21zPK14qVA8BEE1no5r9jrjOlwPFVUhEbHxfnCn_vVqZTAZmMyiKCFlfo1qB7cVzVXK8YFNb-XAqg8lNQHXTW4_4pCJgbTe5m50156fz_zpj1201gJgLhjjNBGS_rj52T6wjKyU1UBpHtQNMQTLX9Hse2qo8ZB4YRPmfn8M2av-aY5DDe7ddf4GStr44SwSNc0lhL2D3lYuYH6AcsIWjDlFEkUXnUsWpMKOUCHFtH2OiLK4G3Fow8S4tcgpY2Ar8vn1z_jKpfp1kSSaaDKrzN6R8r9jYkS7dm8FAXV73ou-ld8`,
                'tokenApi': '2y12Mt3zwhPRAh3eKAJNjKV.julJr.YHYJaAIDlPl1h43oRdOoiceZYFy',
                'Access-Control-Allow-Origin': 'http://localhost:8080',
                'access-control-allow-credentials': 'true',
                'access-control-allow-headers': '*',
                'access-control-allow-methods': '*',
                'oorigin': 'https://englishtest.vn'

            },
            body: lang ? JSON.stringify({text, lang: "en"}) : JSON.stringify({text, lang: "vi"})
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json(); // Giả sử API trả về JSON
        console.log("Phản hồi từ API:", data);
        return data;
    } catch (error) {
        console.error("Lỗi khi gọi API:", error);
        return null;
    }
}


function changeLang() {
    lang ? lang = false : lang = true;
    showWord();
}

document.getElementById("tab-test").addEventListener("click",async () => {
    const response = await fetch(`http://localhost:8080/api/vocab/self-study`);
    vocabList = await response.json();
    currentIndex = 0;
    showWord();
})

document.getElementById("test-btn").addEventListener("click",async () => {
    checkAnswer();
})
