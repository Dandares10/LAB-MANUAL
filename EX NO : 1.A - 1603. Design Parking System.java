class ParkingSystem {
    private int[] sl;
    public ParkingSystem(int big, int medium, int small) {
        sl=new int[4];
        sl[1] = big;
        sl[2] = medium;
        sl[3] = small;
    }

    public boolean addCar(int carType) {
        if(sl[carType] > 0){
            sl[carType]--;
            return true;
        }
        return false;
    }
}
