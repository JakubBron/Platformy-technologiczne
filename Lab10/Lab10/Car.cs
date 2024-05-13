using System;
using System.Collections.Generic;
using System.Text;

namespace Lab10
{
    public class Car
    {
        public string model;
        public int year;
        public Engine motor { get; set; } // getters & setters will be avaliable without need to implement it

        public Car()
        {

        }

        public Car(string modelName, Engine motorOnBoard, int yearOfProduction)
        {
            model = modelName;
            year = yearOfProduction;
            motor = motorOnBoard;
        }

        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            Car otherCar = (Car)obj;
            return (model == otherCar.model && motor.Equals(otherCar.motor) && year == otherCar.year);
        }

        public override int GetHashCode()
        {
            unchecked
            {
                int hash = 17;
                hash = hash * 23 + model.GetHashCode();
                hash = hash * 23 + motor.GetHashCode();
                hash = hash * 23 + year.GetHashCode();
                return hash;
            }
        }


        public void setModel(string modelName)
        {
            model = modelName;
        }

        public string getModel()
        {
            return model;
        }

        public void setYear(int yearOfProduction)
        {
            year = yearOfProduction;
        }

        public int getYear()
        {
            return year;
        }
    }
}
