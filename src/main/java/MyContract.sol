// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.0;

contract MyContract {
    uint public myNumber;

    constructor(uint _myNumber) {
        myNumber = _myNumber;
    }

    function setNumber(uint _newNumber) public {
        myNumber = _newNumber;
    }

    function getNumber() public view returns (uint) {
        return myNumber;
    }
}
