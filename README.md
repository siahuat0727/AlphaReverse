# 簡介
105-2 JAVA軟體開發期末專題 - AlphaReverse(黑白棋 Reversi)
- 組員: 陳聲發、王皓玄、謝永家、吳元智

# 基本功能
- 單人模式
- 對戰模式

# 特點
- 棋盤大小可改
- 可以選擇不同難度AI
- 支援悔棋
- 落子提示
- 可單機可對戰
- ~~支援隨機選一子反轉~~

# 工作分配
- 人工智慧 - 陳聲發
- 棋盤規則 - 陳聲發
- 單人模式 - 謝永家
- 對戰模式 - 王皓玄、陳聲發
- 分數表 - 吳元智
- GUI - 王皓玄
- PowerPoint - 吳元智
- 游戲前期架構 - 謝永家
- 游戲前期使用界面 - 謝永家

# 詳細介紹
- ## AI
    ### 估值
    - 權重: **一般情況下**角最重要，邊次之，角附近尤其是角的對角最弱
    - 行動力 : 一開始起關鍵作用，所以會發現好的AI一開始會盡量不吃子，目的是減少對方行動力的同時也增加己方的行動力
    - 再進階還有 **穩定子** 和 **奇偶性** 等等，但這些實作起來比較麻煩
    - 目前做法 ：取值 = 權重 + 行動力 ， **行動力佔主要比重，可行動點在各個位置有其對應的值**
            
    ### AI_easy
    - 嘗試走目前每一步可走的位置，計算該局勢的估值後，退一步再往其他可走的位置走，若有若干個較好位置則隨機選一個，目的是不要每次遇到同樣情況都走一樣的位置，增加一些隨機性
    - 深度：1步
    
    以上為參考報告前實作的部分
    以下為參考一篇黑白棋報告（[點擊這裡](https://aijunbai.github.io/publications/USTC07-Bai.pdf)）的第3-8頁實作出來的部分（原本想把機器學習以前的部分都寫的但後來發現後面太難實作出來了……）
    
    ### AI_medium
    - 只走一步當然不夠，那再往後搜幾步，要的其實不是能最大化自己的收益，而是最小化自己的虧損，也就是找**最差的情況不會太差**的一步
    - 通過文中介紹的極大極小博弈樹，往下搜4步以內的**所有可能情況**，再調用之前寫好的**估值函數**來選出最不差的一步
    - 深度 ：4步（最長3秒左右得出結果）

    ### AI_hard
    - 由於中局雙方可能都有超過15步的可走位置，那假設要將深度提升至5步，就必須跑完15^5的情況，這樣會計算很久
    - 通過文中介紹的alpha-beta剪枝，一樣用極大極小博弈樹往下搜5步以內的**所有有機會是最好的位置**，一樣調用之前寫好的**估值函數**來選出最不差的一步
    - 深度 ：5步（最長5秒左右得出結果）
    
    ### 額外發想
    - 後來嘗試用AI_medium對戰AI_hard，發現AI_medium竟然領先，於是想說終局部分應該不可以再調用**估值函數**，而是直接以**子數**為準，往下搜到結束部分（一定要搜到結束是因為覺得少了估值，最後一步其實很有機會扭轉乾坤）
    - 所以AI_medium和AI_hard都根據各自的能力在快結束時使用直接暴力搜到結束的方式，於是AI_hard就沒再輸過AI_medium了
    - 深度 ：medium 8 步， hard 10 步（最長5秒左右得出結果）

- ## Server and Client
	### void LAN.Write(String inp, String ServerIP) (發送訊息到Server端)
	- 先設定好Socket，然後透過DataOutputStream的**writeUTF**的方式把訊息傳送到Server端。

	### String LAN.Read() (從Client端接受訊息)
	- 先設定好Socket，然後透過DataInputStream的**readUTH**的方式把訊息接受到String裡面。

	### 如何在開始遊戲時候鏈接對手
	- 在Host先選擇黑棋/白棋，然後呼叫**LAN.Read()**等待Player連線。
	- 開啟Player，然後選擇開始遊戲。當按下開始遊戲，Player會發送"CONNECT"訊息到Host。
	- 如果Host/Player是**黑棋**，那就開始遊戲，等玩家"下棋"時會發送所下的棋子的坐標。
	- 如果Host/Player是**白棋**，那就會執行LAN.Read()，等待Player下棋(~~發送訊息~~)。

	### 在遊戲期間，如何進行通訊
	- 當按下按鈕(下棋)時，程式會先Update棋盤上的訊息，然後透過LAN.Write發送下的棋子的**坐標**，然後等待對手回復。
	- 當收到對手的回復後，會Update棋盤上的訊息，然後顯示可下棋的點。

	### 如何在LAN.Read()的時候swing界面不會卡頓~~無法動作~~
	- 把LAN.Read()放到一個Thread裡面，然後只呼叫Thread來等待對手回復。
