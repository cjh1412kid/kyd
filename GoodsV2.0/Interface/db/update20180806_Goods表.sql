U S E   [ Y H S m a r t R e t a i l ]  
 G O  
  
 A L T E R   T A B L E   [ d b o ] . [ Y H S R _ G o o d s _ S h o e s ]   a d d   C o m p a n y _ S e q   I N T   N U L L  
 G O  
  
 U P D A T E    
 	 [ d b o ] . [ Y H S R _ G o o d s _ S h o e s ]    
 S E T    
 	 C o m p a n y _ S e q   =   T . C o m p a n y _ S e q    
 F R O M    
 	 [ d b o ] . [ Y H S R _ G o o d s _ S h o e s ]   A ,    
 	 ( S E L E C T    
 	 	 A . P e r i o d _ S e q ,   C . C o m p a n y _ S e q    
 	   F R O M   [ d b o ] . [ Y H S R _ G o o d s _ S h o e s ]   A    
 	   L E F T   J O I N   [ d b o ] . [ Y H S R _ G o o d s _ P e r i o d ]   B   O N   A . P e r i o d _ S e q   =   B . S e q  
 	   L E F T   J O I N   [ d b o ] . [ Y H S R _ B a s e _ B r a n d ]   C   O N   B . B r a n d _ S e q   =   C . S e q  
 	   )   T    
 W H E R E    
 	 A . P e r i o d _ S e q   =   T . P e r i o d _ S e q  
 G O  
  
 A L T E R   T A B L E   [ d b o ] . [ Y H S R _ G o o d s _ S h o e s ]   a l t e r   c o l u m n   C o m p a n y _ S e q   I N T   N O T   N U L L  
 G O  
  
  
  
  
 A L T E R   T A B L E   [ d b o ] . [ Y H S R _ G o o d s _ S h o e s ]   a d d    
 [ S X 1 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 2 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 3 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 4 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 5 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 6 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 7 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 8 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 9 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 1 0 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 1 1 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 1 2 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 1 3 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 1 4 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 1 5 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 1 6 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 1 7 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 1 8 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 1 9 ]   v a r c h a r ( 6 )   N U L L   ,  
 [ S X 2 0 ]   v a r c h a r ( 6 )   N U L L    
 G O 