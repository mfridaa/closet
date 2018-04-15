//
//  ViewController.swift
//  Trax
//
//  Created by Kiss Levente on 2018. 02. 28..
//  Copyright © 2018. Kiss Levente. All rights reserved.
//

import UIKit
import MapKit
import CoreLocation
//CLLocationManagerDelegate
class ToiletTableViewViewController: UIViewController,MKMapViewDelegate,CLLocationManagerDelegate {
    let creator:Int16 = 1111
    
    
    struct Constans{
        public static let ShowDetailsSegue = "Show details"
    }
    
    // MARK: toiletBarItem
    @IBOutlet weak var toiletBarItem: UITabBarItem!
    // MARK: spinningBar
    @IBOutlet weak var dataComing: UIActivityIndicatorView!
    
    
    private struct Constants {
        static let LeftCalloutFrame = CGRect(x: 0, y: 0, width: 59, height: 59)
        static let AnnotationViewReuseIdentifier = "waypoint"
        static let ShowImageSeque = "Show Image"
        static let EditUserWaypoint = "Edit Waypoint"
    }
    
    private var toilets:[BasicToilet]?{
        didSet{
            if let data = toilets{
               
                addWayPoints(wayPoints: data)
            }
            
        }
    }
    
//    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
//        if let navController = (segue.destination as? UINavigationController){
//            let tableViewController = navController.viewControllers.compactMap({ $0 as? ToiletInformationsTableViewController})
//            if let toiletInformationTableViewController = tableViewController.first,tableViewController.count == 1 {
//                if let annotation = (sender as? MKAnnotation),annotation.title != nil{
//                    toiletInformationTableViewController.toiletName = annotation.title!
//                    
//                    
//                    if let name = annotation.title{
//                        toiletInformationTableViewController.toiletName = name
//                    }
//                }
////                toiletInformationTableViewController.toiletName = "asd"
//                
//                
//            }
//        }
//    }

    
    private func clearWaypoint(){
        mapView?.removeAnnotation(mapView.annotations as! MKAnnotation)
    }
    
    @IBAction func addNewPlace(_ sender: UILongPressGestureRecognizer) {
        if sender.state == .began{
//            let coordinate = mapView.convert(sender.location(in: mapView), toCoordinateFrom: mapView)
//            let ratings = [Float]()
//            let toilet = BasicToilet(name: "newToilet", location: MapCoordinate(latitude: Float(coordinate.latitude), longitude: Float(coordinate.longitude)), rating: 0, status: "", ratings: ratings,creator: creator)
//            mapView.addAnnotation(toilet.MKPAnnotation())
//            postNewToilet(of: toilet)
            
        }
    }
    func addWayPoints(wayPoints : [BasicToilet]){

        mapView?.addAnnotations(wayPoints.map({$0.MKPAnnotation()}))
        mapView?.showAnnotations(wayPoints.map({$0.MKPAnnotation()}), animated: true)
    }
    
    @IBOutlet weak var mapView: MKMapView!
        {
        didSet{
            mapView.mapType = .standard
            mapView.delegate = self
        }
    }
    
    
    func mapView(_ mapView: MKMapView, viewFor annotation: MKAnnotation) -> MKAnnotationView? {
        if annotation.title! == "My Location"{

            return nil
        }

        var annotationView: MKAnnotationView! = mapView.dequeueReusableAnnotationView(withIdentifier: Constants.AnnotationViewReuseIdentifier)
        
        if annotationView == nil {
            annotationView = MKAnnotationView(annotation: annotation, reuseIdentifier: Constants.AnnotationViewReuseIdentifier)
            annotationView.canShowCallout = true

        } else {
            annotationView.annotation = annotation

        }
        //MAKR: Button for segue to more detail
        let detailButton = UIButton.init(type: UIButtonType.detailDisclosure)
        detailButton.isHidden = false
        annotationView.image = #imageLiteral(resourceName: "CleanToilet")


        annotationView.rightCalloutAccessoryView = detailButton
        annotationView.detailCalloutAccessoryView = nil
        return annotationView
    }
    
    //MARK: -- Informations button pressed
    func mapView(_ mapView: MKMapView, annotationView view: MKAnnotationView, calloutAccessoryControlTapped control: UIControl) {
        if let button = (control as? UIButton),button.buttonType == UIButtonType.detailDisclosure  {
            mapView.deselectAnnotation(view.annotation, animated: false)
            if let navigationParent =  (parent as? NavigationTabBarViewController) {
                navigationParent.performSegue(withIdentifier: "Show informations", sender: view.annotation)
            }
        }
    }
    
    //Location
    
    let manager = CLLocationManager()

    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        let location = locations[0]

        
        
        let myLocation: CLLocationCoordinate2D = CLLocationCoordinate2DMake(location.coordinate.latitude, location.coordinate.longitude)
        
        let span:MKCoordinateSpan = MKCoordinateSpanMake(0.01, 0.01)
        let region: MKCoordinateRegion = MKCoordinateRegionMake(myLocation, span)
        
        mapView.setRegion(region, animated: true)
        mapView.showsUserLocation = true
    }
    
   
    override func viewDidLoad() {
        super.viewDidLoad()
    
        manager.delegate = self
        manager.requestAlwaysAuthorization()
        manager.desiredAccuracy = kCLLocationAccuracyBest
        manager.startUpdatingLocation()
        
    }
    
    //MARK: spinningBar status
    public func processStarted(){
        dataComing.startAnimating()
        mapView.isHidden = true
    }
    
    public func processTerminated(with result: [BasicToilet]){
        toilets = result
        dataComing.stopAnimating()
        mapView.isHidden = false
    }
    

}



