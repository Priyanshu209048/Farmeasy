const messagesDiv = document.getElementById("messages");
const userInput = document.getElementById("user-input");
const sendBtn = document.getElementById("send-btn");
const fileInput = document.getElementById("file-input");
const chatHistoryDiv = document.getElementById("chat-history");

const GEMINI_API_KEY = "AIzaSyA2hHnaUpgLSQyBEjP9gdlHbcKDquMkOmc";

let chatHistory = JSON.parse(localStorage.getItem("chatHistory")) || [];

function addMessage(text, sender) {
  const message = document.createElement("div");
  message.className = `message ${sender}`;
  message.innerText = text;
  messagesDiv.appendChild(message);
  messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

async function getGeminiResponse(userMessage) {
  const url = `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${GEMINI_API_KEY}`;
  
  const requestBody = {
    contents: [{ parts: [{ text: userMessage }] }]
  };

  try {
    const response = await fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(requestBody),
    });

    const data = await response.json();
    if (data.candidates && data.candidates.length > 0) {
      return data.candidates[0].content.parts[0].text;
    } else {
      return "Sorry, I couldn't process your request.";
    }
  } catch (error) {
    console.error("Error fetching from Gemini:", error);
    return "Error contacting AI service.";
  }
}

async function handleUserInput() {
  const userMessage = userInput.value.trim();
  if (userMessage) {
    addMessage(userMessage, "user");

    addToChatHistory(userMessage);

    const botResponse = await getGeminiResponse(userMessage);
    setTimeout(() => addMessage(botResponse, "bot"), 500);

    userInput.value = "";
  }
}

sendBtn.addEventListener("click", handleUserInput);
userInput.addEventListener("keypress", (e) => {
  if (e.key === "Enter") handleUserInput();
});

fileInput.addEventListener("change", handleFileUpload);

async function handleFileUpload(event) {
  const file = event.target.files[0];
  if (!file) return;

  if (file.type === "application/pdf") {
    readPDF(file);
  } else if (file.type.includes("text") || file.type.includes("document")) {
    readTextFile(file);
  } else {
    alert("❌ Unsupported file format. Please upload PDF, TXT, DOC, DOCX.");
  }
}

function readTextFile(file) {
  const reader = new FileReader();
  reader.onload = async function (e) {
    const text = e.target.result;
    addMessage("[📄 File Uploaded]\n" + text, "user");
    await sendTextToGemini(text);
  };
  reader.readAsText(file);
}

function readPDF(file) {
  const reader = new FileReader();
  reader.onload = async function (e) {
    const typedArray = new Uint8Array(e.target.result);
    pdfjsLib.getDocument(typedArray).promise.then(async function (pdf) {
      let text = '';
      for (let i = 1; i <= pdf.numPages; i++) {
        const page = await pdf.getPage(i);
        const textContent = await page.getTextContent();
        text += textContent.items.map(item => item.str).join(' ');
      }
      addMessage("[📄 File Uploaded]\n" + text, "user");
      await sendTextToGemini(text);
    });
  };
  reader.readAsArrayBuffer(file);
}

async function sendTextToGemini(text) {
  addMessage("⏳ Processing file... Please wait.", "bot");
  
  const response = await getGeminiResponse(text);
  setTimeout(() => addMessage(response, "bot"), 500);
}

async function addToChatHistory(question) {
  const shortQuestion = question.length > 40 ? question.substring(0, 40) + "..." : question;
  
  chatHistory.push(shortQuestion);
  localStorage.setItem("chatHistory", JSON.stringify(chatHistory));
  
  updateChatHistory();
}

async function updateChatHistory() {
  chatHistoryDiv.innerHTML = "";
  let historyToShow = chatHistory;

  historyToShow.forEach((item, index) => {
    const div = document.createElement("div");
    div.className = "chat-item";
    div.innerText = `📌 ${item}`;
    div.onclick = () => loadChat(index);
    chatHistoryDiv.appendChild(div);
  });
}

function loadChat(index) {
  const prompt = chatHistory[index];
  addMessage(`📜 Previous Question: ${prompt}`, "user");

  handleUserInput(prompt);
}

function toggleChatHistory() {
  const history = document.getElementById("chat-history");
  history.style.display = (history.style.display === "none" || history.style.display === "") ? "block" : "none";
}

updateChatHistory();