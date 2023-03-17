class Amicus {
   private String name;
   private Room location;

   public Amicus(String name, Room location){
    this.name = name;
    this.location = location;
   }
   
   public String getName(){
    return this.name;
   }
    
   public Room getLocation(){
    return this.location;
   }
}
