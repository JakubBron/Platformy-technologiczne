﻿using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;

namespace Lab10
{
    public class SearchableAndSortableBindingList : BindingList<Car>
    {
        private bool _bModel = false;
        private bool _bYear = false;
        private bool _bMotor = false;

        public SearchableAndSortableBindingList(List<Car> cars)
        {
            foreach (var car in cars)
            {
                Add(car);
            }
        }
        public List<Car> Find(string text, string combo)
        {
            var matchingCars = new List<Car>();

            foreach (var car in this)
            {
                switch (combo)
                {
                    case "Model":
                        {
                            if (car.model == text)
                            {
                                matchingCars.Add(car);
                            }

                            break;
                        }
                    case "Year":
                        {
                            if (car.year == int.Parse(text))
                            {
                                matchingCars.Add(car);
                            }

                            break;
                        }
                    case "Motor":
                        {
                            if (car.motor.model == text)
                            {
                                matchingCars.Add(car);
                            }

                            break;
                        }
                }
            }

            return matchingCars;
        }

        public List<Car> AddElement(string model, string engineModel, double horsepower, double displacement, int year)
        {
            var matchingCars = this.ToList();
            matchingCars.Add(new Car(model, new Engine(displacement, horsepower, engineModel), year));
            return matchingCars;
        }

        public List<Car> RemoveElement(string model, string engineModel, double horsepower, double displacement, int year)
        {
            var matchingCars = this.ToList();
            Car toRemove = new Car(model, new Engine(displacement, horsepower, engineModel), year);
            matchingCars.Remove(toRemove);
            return matchingCars;
        }


        public List<Car> Sort(string property)
        {
            var matchingCars = this.ToList();

            switch (property)
            {
                case "Model":
                    {
                        _bModel = !_bModel;
                        if (_bModel) return matchingCars = matchingCars.OrderBy(car => car.model).ToList();
                        return matchingCars = matchingCars.OrderByDescending(car => car.model).ToList();
                    }
                case "Year":
                    {
                        _bYear = !_bYear;
                        if (_bYear) return matchingCars = matchingCars.OrderBy(car => car.year).ToList();
                        return matchingCars = matchingCars.OrderByDescending(car => car.year).ToList();
                    }
                default:
                    {
                        _bMotor = !_bMotor;
                        if (_bMotor) return matchingCars = matchingCars.OrderBy(car => car.motor.model).ToList();
                        return matchingCars = matchingCars.OrderByDescending(car => car.motor.model).ToList();
                    }
            }
        }
    }
}