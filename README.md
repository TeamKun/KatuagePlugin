# KatuagePlugin
**概要：ジャンプをするとプレイヤーからアイテムがランダムでドロップする。また、他プレーヤの背後からスニークして殴るとアイテムがドロップする。**  
**動作確認環境：Minecraft1.15.2、Paper-1.15.2**  
**外部プラグインとの連携:MoneyCraft1.5.6との連携に対応**  
(Money 関係のコマンドを実行するにはMoneyCraft1.5.6およびその前提プラグインが導入されている必要があります。)  
## Config.yml(コンフィグ設定)  
*すべての数値は初期設定で表記  
**プラグイン全体の設定**  
*・PluginOnOff: true*  
　プラグインのオン/オフ(true,false)  

**ランダムドロップ関係の設定**  
*・RandomDropOnOff: true*  
　ランダムドロップのオン/オフ(true,false)  
  
*・RandomDropProbability: 70*  
　ランダムドロップの確率設定(0~100)
  
*・Item1~18:*  
   　　*==: org.bukkit.inventory.ItemStack*  
   　　*type: STICK*  
　ランダムドロップアイテムの設定(type: アイテム名)
     
 **インベントリドロップ関係の設定**  
*・InventoryDropOnOff: true*  
　インベントリドロップのオン/オフ(true,false)  
   
*・InventoryDropProbability: 70*  
　インベントリドロップの確率設定(0~100)  
 
  **ダメージドロップ関係の設定**  
*・InventoryDropOnOff: true*  
　ダメージドロップのオン/オフ(true,false)  
   
*・InventoryDropProbability: 100*  
　ダメージドロップの確率設定(0~100)  
 
 **マネークラフト関係の設定**  
 *・MoneyDropOnOff*  
 　マネードロップのオン/オフ  
   
 *・MoneyDropProbability*  
 　マネードロップの確立設定(0~100)  
    
 *・MoneyDropType*  
 　マネードロップの硬貨、紙幣の設定(100or1000)  
    
 *・MoneyDropAmount*  
 　マネードロップの枚数の設定  
     
## コマンド  
*コマンドの実行にはOP権限が必要  
*・/katuage main on , /katuage main off*  
　プラグインのon/offの設定  
   
*・/katuage rand on , /katuage rand off*  
　ランダムドロップのon/offの設定  
   
*・/katuage randP #0〜100の数字*  
　ランダムドロップ確率の変更(0〜100%) 
   
*・/katuage item set*  
　ランダムドロップされるアイテムを設定できるインベントリの表示  
   
*・/katuage inve on , /katuage inve off*  
　インベントリドロップのon/offの設定  
   
*・/katuage inveP #0〜100の数字*  
　インベントリドロップの確率の設定(0〜100%)  
 
*・/katuage dame on , /katuage dame off*  
　ダメージドロップのon/offの設定  
   
*・/katuage dameP #0〜100の数字*  
　ダメージドロップの確率の設定(0〜100%)  
   
*・/katuage money on , /katuage money off*  
　マネードロップのon/offの設定  
   
*・/katuage moneyP #0〜100の数字*  
　マネードロップの確率の設定(0〜100%)  
   
*・/katuage moneyA #1以上の整数*  
　マネードロップの枚数設定  
   
*・/katuage moneyT #100 or 1000*  
　マネードロップの硬貨・貨幣の種類設定  
   
 
   
