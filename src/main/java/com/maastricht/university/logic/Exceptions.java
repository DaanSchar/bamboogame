package com.maastricht.university.logic;

class IllegalMoveException extends Exception {

    public IllegalMoveException(String e){
        super(e);
    }

}

class OutsideHexagonException extends Exception{

    public OutsideHexagonException(String e) {
        super(e);
    }
}