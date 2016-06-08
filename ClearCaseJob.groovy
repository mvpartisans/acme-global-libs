def call(def cl) {
    try{
        cl();   
    } catch(Exception e) {
        println "***************************************"
        println e.getMessage();
    } finally {
        utils.clearCaseRemoveView("FunApp");        
    }   
}

return this;
